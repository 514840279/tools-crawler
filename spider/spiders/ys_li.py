# -*- coding: utf-8 -*-
"""
Created on 2022-04-05 17:38:32
---------
@summary:
---------
@author: wth
"""

import feapder
from feapder.db.mysqldb import MysqlDB
from items import 不动产登记中心_商品房预售房源基本信息_item


class YsLi(feapder.AirSpider):
    def start_requests(self):
        db = MysqlDB()
        sql = "select id,工程链接 from 不动产登记中心_商品房预售房源 where delete_flag = 0"
        flag = True
        while flag:
            try:
                rows = db.find(sql=sql, limit=1000, to_json=True)
                for row in rows:
                    id = row['id']
                    url = row['工程链接']
                    lid = url.replace('http://124.93.228.101:8087/bd/tgxm/getLi?lid=','')
                    yield feapder.Request(url,id=id,lid=lid)
            finally:
                flag = False
                pass

    def parse(self, request, response):
        trs = response.xpath('//table[@class="table table-bordered FCtable"]/tr')
        for tr in trs:
            tds = tr.xpath("./td")
            for td in tds:
                单元楼层房号 = td.xpath('./text()').extract_first()
                if 单元楼层房号:
                    item = 不动产登记中心_商品房预售房源基本信息_item.不动产登记中心商品房预售房源基本信息Item()
                    title = td.xpath('./@title').extract_first()
                    style = td.xpath('./@style').extract_first()
                    if 'color: black' == style:
                        item.可售 = "可售"
                    elif 'color: green' == style or 'color: #00CC00' == style:
                        item.可售 = "已售"
                    elif 'color: blue' == style:
                        item.可售 = "已被开发企业抵押给金融机构，暂不可售 "
                    elif 'color: red' == style:
                        item.可售 = "不可售（因超建、查封、物业用房、回迁安置等原因）。"

                    item.房源id = request.id
                    item.lid = request.lid
                    dlf = 单元楼层房号.replace('--','-').split('-')

                    item.单元 = dlf[0].strip()
                    item.楼层 = dlf[1].strip()
                    item.房号 = dlf[2].replace(' ','').replace('\r\n','')

                    ylm = title.split('，')
                    for st in ylm:
                        map = st.split('：')
                        if map[0] == "房屋用途":
                            item.房屋用途 = map[1]
                        elif map[0] == "房屋类型":
                            item.房屋类型 = map[1]
                        elif map[0] == "建筑面积":
                            item.建筑面积 = map[1]
                        elif map[0] == "套内面积":
                            item.套内面积 = map[1]
                        elif map[0] == "预售价格上限":
                            item.预售价格上限 = map[1]
                        else:
                            item.other = title
                    yield item

        db = MysqlDB()
        sql = "update 不动产登记中心_商品房预售房源 set delete_flag = 1 where delete_flag = 0 and id = %s" %request.id
        db.update(sql)

if __name__ == "__main__":
    YsLi().start()