// 任务信息
export interface Job{
    id:number,
    regno:string,
    companyName: string,
    anCheYear:string,
    flag:number,
    insertTime:Date
}

export interface PageParam{
    pageNumber: number,
    pageSize: Number,
    totalElements: Number,
    info?: Object,
}