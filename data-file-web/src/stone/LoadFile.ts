import { defineStore } from 'pinia'
import { SysLoadFileColsInfo, SysLoadFileColsMapping, SysLoadFileInfo } from '../interface/LoadFile'
import { SysDbmsTabsCols, SysDbmsTabsTableInfo } from '../interface/SysDbms';

let fileInfo :SysLoadFileInfo;
let fileColumns: Array<SysLoadFileColsInfo>;
let fileColsMapping: Array<SysLoadFileColsMapping>;
let tableInfo: SysDbmsTabsTableInfo;
let tableColumnsInfo: Array<SysDbmsTabsCols>

// useStore could be anything like useUser, useCart
// the first argument is a unique id of the store across your application
export const loadStore = defineStore('load', {
  // other options...
  state: () => {
    return {
      fileInfo:fileInfo,
      fileColumns:fileColumns,
      fileColsMapping:fileColsMapping,
      tableInfo:tableInfo,
      tableColumnsInfo:tableColumnsInfo
    }
},

getters: {},
actions: {}
})