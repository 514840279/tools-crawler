# -*- coding: utf-8 -*-
"""
Created on 2022-04-05 17:38:32
---------
@summary:
---------
@author: wth
"""
from time import sleep

import feapder
from feapder.db.mysqldb import MysqlDB
from items import 不动产登记中心_商品房预售房源基本信息_item


class YsLi(feapder.AirSpider):
    db = MysqlDB()

    def start_requests(self):

        sql1 = "select id,工程链接,lid,xmid from 不动产登记中心_商品房预售房源 where delete_flag = 0"
        limit = 10  # 分页 大于1
        timeout = 30000.00  # 超时

        rows = self.db.find(sql=sql1, limit=limit, to_json=True)
        while rows:
            for row in rows:
                rid = row['id']
                url = row['工程链接']
                lid = row['lid']
                xmid = row['xmid']
                # print(row)
                yield feapder.Request(url, is_abandoned=True, timeout=timeout, id=rid, lid=lid,xmid=xmid)
            sleep(10)
            rows = self.db.find(sql=sql1, limit=limit, to_json=True)
            # print(len(rows))

    def parse(self, request, response):
        tds = response.xpath('//table[@class="XkbTable"]//tr//td/span')
        房子数量 = 0
        if tds:
            se = "select id,房源id from 不动产登记中心_商品房预售房源基本信息 where 房源id=%s " % request.id
            fy = self.db.find(sql=se, limit=2, to_json=True)
            if fy:
                self.db.delete("delete from 不动产登记中心_商品房预售房源基本信息 where 房源id =%s" % request.id)
            for td in tds:
                单元楼层房号 = td.xpath('./text()').extract_first()
                if 单元楼层房号:
                    房子数量 += 1
                    item = 不动产登记中心_商品房预售房源基本信息_item.不动产登记中心商品房预售房源基本信息Item()
                    title = td.xpath('./@title').extract_first()
                    style = td.xpath('./@style').extract_first()
                    if 'BACKGROUND-COLOR: ff8c1a' == style:
                        item.可售 = "已登记"
                    elif 'BACKGROUND-COLOR: green' == style:
                        item.可售 = "可售"
                    elif 'BACKGROUND-COLOR: blue' == style:
                        item.可售 = "限制销售 "
                    elif 'BACKGROUND-COLOR: 80FFFF' == style:
                        item.可售 = "在建抵押"
                    elif 'BACKGROUND-COLOR: ff71ff' == style:
                        item.可售 = "不可售"
                    elif 'BACKGROUND-COLOR: red' == style:
                        item.可售 = "已售"
                    elif 'BACKGROUND-COLOR: #ffff1e' == style:
                        item.可售 = "已签"

                    item.房源id = request.id
                    item.lid = request.lid
                    item.xmid = request.xmid

                    dlf = 单元楼层房号.replace('--', '-负').replace('单元', '-').replace('楼', '-').replace('幢', '-').replace('层', '-').replace('号', '').split('-')

                    item.单元 = dlf[0].strip()
                    item.楼层 = dlf[1].strip()
                    item.房号 = dlf[2].replace(' ', '').strip()

                    ylm = title.split('\n')
                    for st in ylm:
                        map = st.split('：')
                        if map[0] == "房屋用途":
                            item.房屋用途 = map[1]
                        elif map[0] == "坐落":
                            item.坐落 = map[1]
                        elif map[0] == "房屋类型":
                            item.房屋类型 = map[1]
                        elif map[0] == "面积":
                            item.建筑面积 = map[1]
                        elif map[0] == "套内面积":
                            item.套内面积 = map[1]
                        elif map[0] == "预售限制单价":
                            item.预售价格上限 = map[1]
                        elif map[0] == "换手率":
                            item.换手率 = map[1]
                        else:
                            item.other = title
                    # print(item)
                    # self.db.add_smart(table="不动产登记中心_商品房预售房源基本信息", data=item.to_dict)
                    yield item

        tds = response.xpath('//table[@class="table table-bordered FCtable"]/tr/td')
        if tds:
            se = "select id,房源id from 不动产登记中心_商品房预售房源基本信息 where 房源id=%s " % request.id
            fy = self.db.find(sql=se, limit=2, to_json=True)
            if fy:
                self.db.delete("delete from 不动产登记中心_商品房预售房源基本信息 where 房源id =%s" % request.id)
        for td in tds:
            单元楼层房号 = td.xpath('./text()').extract_first()
            if 单元楼层房号:
                房子数量 += 1
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

                dlf = 单元楼层房号.replace('--', '-负').replace('单元', '-').replace('楼', '-').replace('幢', '-').replace('层', '-').replace('号', '').split('-')

                item.单元 = dlf[0].strip()
                item.楼层 = dlf[1].strip()
                item.房号 = dlf[2].replace(' ', '').replace('\r\n', '')

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
                    elif map[0] == "换手率":
                        item.换手率 = map[1]
                    else:
                        item.other = title
                yield item

        sql = "update 不动产登记中心_商品房预售房源 set delete_flag = 1,房子数量 = %s where delete_flag = 0 and id = %s" % (房子数量, request.id)
        self.db.update(sql)

    def exception_request(self, request, response):
        print(request.id, "exception.")
        usql = "update 不动产登记中心_商品房预售房源 set delete_flag = -1 where delete_flag = 0 and id = %s" % request.id
        self.db.update(usql)

    def failed_request(self, request, response):
        usql = "update 不动产登记中心_商品房预售房源 set delete_flag = -2 where delete_flag = 0 and id = %s" % request.id
        self.db.update(usql)


if __name__ == "__main__":
    YsLi(thread_count=10).start()
