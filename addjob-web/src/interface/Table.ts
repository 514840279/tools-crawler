export interface PageParam{
    pageNumber: number,
    sizes: Array<Number>,
    pageSize: Number,
    totalElements: Number,
    info?: Object,
    sortList?: Array<SortColumn>,
    searchList?: Array<SearchParamters>
}
export interface Column{
    name: String,
    title: String,
    width?: Number,
    resizable?: Boolean,
    align?:String,
    show?: Boolean,
    sort?: Boolean,
    sortOrder?:String,
    type?:string,
    searchDataFormatter?: String,
    searchDataDefault?: String,
    searchDataArray?:String,
    search?: boolean,
    disable?:boolean
}

export interface SortColumn{
    sortIndex: number,
    sortTitle: String,
    sortOrder: String,
    sortName: String,
}
export interface SearchColumn{
    searchName: String,
    searchTitle: String,
    searchType: String,
    searchDataFormatter?: String,
    searchDataDefault?:String,
    searchDataArray?:String,
    searchPlaceholder?: String,
}

export interface SearchParamters{
    operator: String,
    column: String,
    title: String,
    symbol: String,
    data: String,
    searchPlaceholder?: String,
    showdata: Boolean
}

export interface TableProps{
    rootUrl?:String,
    page?: PageParam,
    columns?: Array<Column>,
    optionBtn?:Object
}

export interface OptionBtn{
    search?: boolean, // 开启查询功能
    searchParam?: boolean, // 开启查询功能
    sort?: boolean, // 开启排序功能
    add?: boolean, // 添加
    page?: boolean, // 翻页
    opt?: boolean, // 每条数据后端操作搭配optbtn使用
    optbtn?: Optbtn
}
interface Optbtn{
    info?: boolean, // 详细 暂时无用
    upd?: boolean, // 修改
    state?: boolean, // 修改表中应有固定字段 delete_flag 默认值为0 逻辑删除字段 执行update 
    del?: boolean, // 删除 执行delete sql
}


// 控制字段类型的类型
export enum SearchType{
    TEXT = 'text',
    INTEGER = 'integer',
    INTEGER_RANGE = 'integerrange',
    ICON = 'icon',
    COLOR = 'color',
    DATE = 'date',
    DATETIME = 'datetime',
    DATERANGE = 'daterange',
    SELECT = 'select',
    REDIO = 'redio',
    CHECKBOX = 'checkbox',
    OPERATION = 'operation'
}