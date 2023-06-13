<template>
    <div>
        <div style="display:inline;margin-left: 10px;" v-if = "showPause">
            <a-button size="small" @click="handleStop"><PauseCircleOutlined/>{{stopFlag?'继续上传':'暂停'}}</a-button>
        </div>
        <div style="float:none;display:inline">
            <a-upload
            :accept="props.accept"
            :beforeUpload="handleBeforeUpload"
            :customRequest="uploadBigFile"
            style="float: inline-start;"
            :showUploadList="false"
            >
                <a-button type="primary" size="small" >
                    <CloudUploadOutlined />
                    {{props.label}}
                </a-button>
            </a-upload>
        </div>
        <Progress :percent="percent" v-if="isShow"/>
    </div>
</template>

<script lang="ts" setup name="BigFileupload">
	import {ref} from 'vue';
  import { propTypes } from '/@/utils/propTypes';
  import SparkMD5 from 'spark-md5';
  import { defHttp } from '/@/utils/http/axios';
  import { uploadUrl, checkUploadUrl, updatePkgInfoUrl } from "../OpPkgParent.api";
  import { message, Upload} from 'ant-design-vue';
  import {CloudUploadOutlined,PauseCircleOutlined } from '@ant-design/icons-vue';
  import Progress from 'ant-design-vue/lib/progress'; // 加载 JS

    //分片大小
    const CHUNK_SIZE = 20 * 1024 * 1024;
    let chunkList: any = [];
    let totalSize: any = 0;
    let successChunks: any = [];
    let percent = ref(0);
    let stopFlag = ref(false);
    let md5: any;
    let fileName: string;
    let isShow = ref();
    const props = defineProps({
        path: propTypes.string,
        onSuccess : propTypes.func,
        label : propTypes.string,
        accept : propTypes.string,
        gameId: propTypes.integer,
        subGameId: propTypes.integer
    })
    const showPause = ref(false);
    const apkType = ['application/vnd.android.package-archive'];

    function handleBeforeUpload(file) {
        chunkList = [];
        percent.value = 0;
        totalSize = 0;
        successChunks = [];
		md5 = '';
        isShow.value = true;
        stopFlag.value = false
        if(!apkType.includes(file.type)){
            message.error("上传文件类型错误");
            return false || Upload.LIST_IGNORE;
        }
    }
	function handleStop(){
        stopFlag.value = !stopFlag.value;
        if(!stopFlag.value){
            resume();
        }
	}
	function resume(){
		if(md5 != undefined && successChunks.length < chunkList.length){
			let len = chunkList.length;
			var i = 0;
			let doUpload = async function(){
				if(!stopFlag.value){
                    if(successChunks.includes(i+1)){  
                        i=i+1;
                        doUpload();
                        return;
                    }
                    console.log(i)
                    let data:any = {
                        data :{
                            file : chunkList[i],
                            identifier : md5,
                            chunkNumber: i + 1,
                            fileName : fileName,
                            chunkSize : CHUNK_SIZE,
                            relativePath : props.path ==undefined?'':props.path,
                            currentChunkSize : chunkList[i].size,
                            totalChunk : len,
                            totalSize : totalSize,
                            gameId : props.gameId,
                            subGameId : props.subGameId
                        }
                    };
                    //定义回调
                    const chunkUploadCallBack = {
                        // success : doFileUploadCallBack,
                        isReturnResponse : true
                    };
                    let res = await defHttp.uploadFile({url : uploadUrl},  data,chunkUploadCallBack);
                    if(res.code == 200){
                        successChunks.push(res.result.chunkNumber);
                        percent.value = percent.value + Math.round((res.result.currentChunkSize/totalSize)*100)
                        if(successChunks.length >= len){
                            percent.value = 100;
                            isShow.value = false;
                            if (props.onSuccess != undefined) {
                                props.onSuccess();
                            }
                            showPause.value = false;
                            return;
                        }
                    } 
                    i=i+1;
                    doUpload();

				}else{
					message.info("暂停")
				}
			}
			doUpload();
		}
    }

  	async function uploadBigFile(params : any){
        showPause.value = true;
        const file =  params.file;
        fileName = file.name
        totalSize = file.size;
        md5 = await computeMD5(file);
        let res =  await defHttp.get({url: checkUploadUrl, params :{identifier : md5, gameId: props.gameId, subGameId: props.subGameId}});
        successChunks = res.uploadedChunks;
        if(successChunks.length >= chunkList.length){
            message.success("上传成功");
            isShow.value = false;
            defHttp.get({url : updatePkgInfoUrl, params: {gameId : props.gameId , subGameId : props.subGameId, fileName: fileName}});
            if(props.onSuccess != undefined){
                props.onSuccess();
            }
            showPause.value = false;
            return;
        }
        resume();
    }
    /**
     * 计算文件MD5值
     * @param file 组件传的文件
     */
    function computeMD5(file: any) {
        message.loading('文件切片中请稍等')
        return new Promise((resolve, reject)=>{
        try {
            let fileReader = new FileReader();
            let time = new Date().getTime();
            let blobSlice = File.prototype.slice

            let currentChunk = 0;
            let chunks = Math.ceil(file.size / CHUNK_SIZE);
            let spark = new SparkMD5.ArrayBuffer();
            loadNext();
            fileReader.onload = ((e: any) => {
                spark.append(e.target.result);
                currentChunk++;
                if (currentChunk < chunks) {
                    loadNext();
                } else {
                    let md5 = spark.end();
                    resolve(md5)
                    console.log(`MD5计算完毕：${file.name} \nMD5：${md5} \n分片：${chunks} 大小:${file.size} 用时：${new Date().getTime() - time} ms`);
                }
            });
            fileReader.onerror = function () {
                // this.error(`文件${file.name}读取出错，请检查该文件`)
                // file.cancel();
            };
            function loadNext() {
                let start = currentChunk * CHUNK_SIZE;
                let end = ((start + CHUNK_SIZE) >= file.size) ? file.size : start + CHUNK_SIZE;
                let result = blobSlice.call(file, start, end);
                fileReader.readAsArrayBuffer(result);
                chunkList.push(result)
            }
        }catch(e) {
            reject(e)
        }
    })
}

</script>