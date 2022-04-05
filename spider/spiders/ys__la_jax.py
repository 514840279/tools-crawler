# -*- coding: utf-8 -*-
"""
Created on 2022-04-05 16:41:15
---------
@summary:
---------
@author: wth
"""

import feapder
from feapder.db.mysqldb import MysqlDB
from items import 不动产登记中心_商品房预售房源_item


class ys_LAjax(feapder.AirSpider):
    def start_requests(self):
        db = MysqlDB()
        sql = "select id,项目链接 from 不动产登记中心_商品房预售许可 where delete_flag = 1"
        rows = db.find(sql=sql, limit=15000, to_json=True)
        urls = "http://124.93.228.101:8087/bd/tgxm/LAjax"
        for row in rows:
            id = row['id']
            url = row['项目链接']
            xmid = url.replace('http://124.93.228.101:8087/bd/tgxm/getTgxm?xmid=','')
            data={
                "xmid":xmid,
                "pageSize": "100",
                "pageNo":"1"
            }
            yield feapder.Request(url=urls,method="post",data=data,id=id,xmid=xmid)

    def parse(self, request, response):
        id = request.id
        trs = response.xpath('//table[@class="table table-bordered FCtable mar-bo"]/tr')
        for tr in trs:
            工程链接 = tr.xpath('./td[1]/a/@href').extract_first()
            if 工程链接:
                item = 不动产登记中心_商品房预售房源_item.不动产登记中心商品房预售房源Item()
                item.xmid = request.xmid
                item.项目id = id
                item.工程链接 = 工程链接
                item.工程楼号 = tr.xpath('./td[1]/a/text()').extract_first()
                item.预售许可证 = tr.xpath('./td[2]/text()').extract_first()
                item.坐落 = tr.xpath('./td[3]/text()').extract_first()
                item.总套数 = tr.xpath('./td[4]/text()').extract_first()
                item.总面积 = tr.xpath('./td[5]/text()').extract_first()

                yield item
        db = MysqlDB()
        sql = "update 不动产登记中心_商品房预售许可  set delete_flag=2 where delete_flag = 1 and id =%s" %id

        db.update(sql)



if __name__ == "__main__":
    ys_LAjax().start()