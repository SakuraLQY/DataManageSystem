<template>
    <Card :tab-list="tabListTitle" v-bind="$attrs" :active-tab-key="activeKey" @tabChange="onTabChange">
        <p v-if = "foo =='0'">
            <!-- 对应的echart图片的展示 -->
        <TotalMoney/>
        </p>
        <p v-if = "foo=='1'">
        <PayUser/>
        </p>
        <p v-if="foo=='2'">
        <NewPayUser/>
        </p>
        <p v-if="foo=='3'">
        <NewRegistryUser/>
        </p>
        <p v-if = "foo=='4'">
          <OnlinePerson/>
        </p>
    </Card>
</template>
<script lang="ts" setup>
import { ref ,inject} from 'vue';
import { Card } from 'ant-design-vue';
import TotalMoney from './TotalMoney.vue';
import PayUser from './PayUser.vue';
import NewPayUser from './NewPayUser.vue';
import NewRegistryUser from './NewRegistryUser.vue';
import OnlinePerson from './OnlinePerson.vue';

const foo:any = inject('tempN');

// const dataSource:any = inject('data')
 
  //点击图标进行值的更新
  function onTabChange(key) {
    activeKey.value = key;
  }
</script>