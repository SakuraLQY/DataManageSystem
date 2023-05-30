package org.jeecg.config.vo;

import java.io.File;

/**
 * @author: scott
 * @date: 2022年04月18日 20:35
 */
public class Path {

  /** 文件上传根目录 设置 */
  private String upload;

  /** webapp文件路径 */
  private String webapp;

  /** 母包文件夹 */
  private String parentPkgFolder;

  /** 一级游戏包文件夹 */
  private String pkgFolder;

  /** 打包素材文件夹 */
  private String packMaterialFolder;

  /** 渠道素材文件夹 */
  private String materialFolder;

  /** 一级游戏包打包广告id */
  private String pkgDealFolder;

  public String getPkgDealFolder() {
    return pkgDealFolder;
  }

  public void setPkgDealFolder(String pkgDealFolder) {
    this.pkgDealFolder = pkgDealFolder;
  }

  public String getUpload() {
    return upload;
  }

  public void setUpload(String upload) {
    this.upload = upload;
  }

  public String getWebapp() {
    return webapp;
  }

  public void setWebapp(String webapp) {
    this.webapp = webapp;
  }

  public String getParentPkgFolder() {
    return parentPkgFolder;
  }

  public void setParentPkgFolder(String parentPkgFolder) {
    this.parentPkgFolder = parentPkgFolder;
  }

  public String getPkgFolder() {
    return pkgFolder;
  }

  public void setPkgFolder(String pkgFolder) {
    this.pkgFolder = pkgFolder;
  }

  public String getPackMaterialFolder() {
    return packMaterialFolder;
  }

  public void setPackMaterialFolder(String packMaterialFolder) {
    this.packMaterialFolder = packMaterialFolder;
  }

  public String getMaterialFolder() {
    return materialFolder;
  }

  public void setMaterialFolder(String materialFolder) {
    this.materialFolder = materialFolder;
  }

  public String parentPkgFolderPath() {
    return this.upload + File.separator + this.parentPkgFolder;
  }

  public String pkgFolderPath() {
    return this.upload + File.separator + this.pkgFolder;
  }

  public String packMaterialFolderPath() {
    return this.upload + File.separator + this.packMaterialFolder;
  }

  public String materialFolderPath() {
    return this.upload + File.separator + this.materialFolder;
  }
}
