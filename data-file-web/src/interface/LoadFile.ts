export enum FileStateTypes {
  "未配置", "配置中"
}

export enum RunStateType {
  "未运行", "运行中", "运行成功", "运行失败"
}

export enum FileType {
  XLSX = ".xlsx",
  XLS = ".xls",
  CSV = ".csv",
  TXT = ".txt",
  JSON = ".json",
  DATA = ".data"
}

// `chuxue`.`sys_load_file_log_info` 数据文件导入时运行信息  
export interface SysLoadFileLogInfo {
  fileUuid?: string,
  complateRows?: number,
  errorRows?: number,
  errorFile?: string,
  uuid: string,
  discription?: string,
  deleteFlag?: number,
  sort?: number,
  createTime?: Date,
  createUser?: string,
  updateTime?: Date,
  updateUser?: string,
  runState?: string,
  errorMessage?: string,
}


// `chuxue`.`sys_load_file_info` 数据文件信息  
export interface SysLoadFileInfo {
  uuid: string,
  path?: string,
  fileName?: string,
  fileType?: string,
  fileSize?: number,
  fileState?: string,
  discription?: string,
  deleteFlag?: number,
  sort?: number,
  createTime?: Date,
  createUser?: string,
  updateTime?: Date,
  updateUser?: string,
  characterset?: string,
  skip?: number,
  separator?: string,
  enclosed?: string,
  hasHead?: string,
  rows?: number,
  fileMappingState?: string
}


// `chuxue`.`sys_load_file_cols_mapping` 数据文件与数据表映射关系  
export interface SysLoadFileColsMapping {
  uuid: string,
  fileUuid?: string,
  fileColumnUuid?: string,
  columnFormat?: string,
  tabsUuid?: string,
  tabsColumnUuid?: string,
  discription?: string,
  deleteFlag?: number,
  sort?: number,
  createTime?: string,
  createUser?: string,
  updateTime?: Date,
  updateUser?: string,
}


// `chuxue`.`sys_load_file_cols_info` 加载数据文件的字段信息  
export interface SysLoadFileColsInfo {
  uuid: string,
  fileUuid: string,
  columnName: string,
  columnDesc?: string,
  columnType?: string,
  columnLength?: number,
  discription?: string,
  deleteFlag?: number,
  sort?: number,
  createTime?: Date,
  createUser?: string,
  updateTime?: Date,
  updateUser?: string,
}


