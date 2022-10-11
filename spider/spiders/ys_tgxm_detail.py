# -*- coding: utf-8 -*-
"""
Created on 2022-04-05 13:44:36
---------
@summary:
---------
@author: wth
"""

import feapder
from feapder.db.mysqldb import MysqlDB
from items import 不动产登记中心_商品房预售许可_item





class YsTgxmDetail(feapder.AirSpider):
    db = MysqlDB()

    def start_requests(self):

        sql = "select id,项目链接,区域名称,项目名称,项目地址,开发企业名称  from 不动产登记中心_商品房预售许可 where delete_flag = 0"
        rows = self.db.find(sql=sql,limit=15000,to_json=True)
        try:
            for row in rows:
                url = row['项目链接']
                yield feapder.Request(url,row = row)
        except:
            pass


    def parse(self, request, response):
        row = request.row
        print(row)
        content = response.xpath('//div[@class="fangyuanSearch02"]')
        item = 不动产登记中心_商品房预售许可_item.不动产登记中心商品房预售许可Item()
        item.id = row['id']
        item.区域名称 = row['区域名称']
        item.项目名称 = row['项目名称']
        item.项目链接 = row['项目链接']
        item.项目地址 = row['项目地址']
        item.开发企业名称 = row['开发企业名称']

        item.预售许可证 = content.xpath('./table[@class="table table-bordered FCtable"]/tr[1]/td[4]/text()').extract_first()
        item.国有土地使用证 = content.xpath('./table[@class="table table-bordered FCtable"]/tr[2]/td[4]/text()').extract_first()
        item.工程规划许可证 = content.xpath('./table[@class="table table-bordered FCtable"]/tr[3]/td[4]/text()').extract_first()

        总套数 = content.xpath('./table[@class="table table-bordered FCtable mar-bo"]/tr[1]/td[1]/b/text()').extract_first()
        if 总套数 == '总套数：':
            item.总套数 = content.xpath('./table[@class="table table-bordered FCtable mar-bo"]/tr[1]/td[2]/text()').extract_first()
            item.总面积 = content.xpath('./table[@class="table table-bordered FCtable mar-bo"]/tr[1]/td[4]/text()').extract_first()
            item.可售总套数 = content.xpath('./table[@class="table table-bordered FCtable mar-bo"]/tr[1]/td[6]/text()').extract_first()
            item.可售总面积 = content.xpath('./table[@class="table table-bordered FCtable mar-bo"]/tr[1]/td[8]/text()').extract_first()
        elif 总套数 == "住宅套数：":
            item.住宅套数 = content.xpath('./table[@class="table table-bordered FCtable mar-bo"]/tr[1]/td[2]/text()').extract_first()
            item.住宅面积 = content.xpath('./table[@class="table table-bordered FCtable mar-bo"]/tr[1]/td[4]/text()').extract_first()
            item.可售住宅套数 = content.xpath('./table[@class="table table-bordered FCtable mar-bo"]/tr[1]/td[6]/text()').extract_first()
            item.可售住宅面积 = content.xpath('./table[@class="table table-bordered FCtable mar-bo"]/tr[1]/td[8]/text()').extract_first()
        elif 总套数 == "非住宅套数：":
            item.非住宅套数 = content.xpath('./table[@class="table table-bordered FCtable mar-bo"]/tr[1]/td[2]/text()').extract_first()
            item.非住宅面积 = content.xpath('./table[@class="table table-bordered FCtable mar-bo"]/tr[1]/td[4]/text()').extract_first()
            item.可售非住宅套数 = content.xpath('./table[@class="table table-bordered FCtable mar-bo"]/tr[1]/td[6]/text()').extract_first()
            item.可售非住宅面积 = content.xpath('./table[@class="table table-bordered FCtable mar-bo"]/tr[1]/td[8]/text()').extract_first()

        住宅套数 = content.xpath('./table[@class="table table-bordered FCtable mar-bo"]/tr[2]/td[1]/b/text()').extract_first()
        if 住宅套数 == "住宅套数：":
            item.住宅套数 = content.xpath('./table[@class="table table-bordered FCtable mar-bo"]/tr[2]/td[2]/text()').extract_first()
            item.住宅面积 = content.xpath('./table[@class="table table-bordered FCtable mar-bo"]/tr[2]/td[4]/text()').extract_first()
            item.可售住宅套数 = content.xpath('./table[@class="table table-bordered FCtable mar-bo"]/tr[2]/td[6]/text()').extract_first()
            item.可售住宅面积 = content.xpath('./table[@class="table table-bordered FCtable mar-bo"]/tr[2]/td[8]/text()').extract_first()
        elif 住宅套数 == "非住宅套数：":
            item.非住宅套数 = content.xpath('./table[@class="table table-bordered FCtable mar-bo"]/tr[2]/td[2]/text()').extract_first()
            item.非住宅面积 = content.xpath('./table[@class="table table-bordered FCtable mar-bo"]/tr[2]/td[4]/text()').extract_first()
            item.可售非住宅套数 = content.xpath('./table[@class="table table-bordered FCtable mar-bo"]/tr[2]/td[6]/text()').extract_first()
            item.可售非住宅面积 = content.xpath('./table[@class="table table-bordered FCtable mar-bo"]/tr[2]/td[8]/text()').extract_first()

        非住宅套数 = content.xpath('./table[@class="table table-bordered FCtable mar-bo"]/tr[3]/td[1]/b/text()').extract_first()
        if 非住宅套数 == "非住宅套数：":
            item.非住宅套数 = content.xpath('./table[@class="table table-bordered FCtable mar-bo"]/tr[3]/td[2]/text()').extract_first()
            item.非住宅面积 = content.xpath('./table[@class="table table-bordered FCtable mar-bo"]/tr[3]/td[4]/text()').extract_first()
            item.可售非住宅套数 = content.xpath('./table[@class="table table-bordered FCtable mar-bo"]/tr[3]/td[6]/text()').extract_first()
            item.可售非住宅面积 = content.xpath('./table[@class="table table-bordered FCtable mar-bo"]/tr[3]/td[8]/text()').extract_first()

        item.delete_flag = 1

        self.db.update_smart(table="不动产登记中心_商品房预售许可",data=item.to_dict,condition="id="+str(row['id']))






if __name__ == "__main__":
    YsTgxmDetail().start()