# -*- coding: utf-8 -*-
"""
Created on 2022-04-05 17:01:14
---------
@summary:
---------
@author: wth
"""

from feapder import Item


class 不动产登记中心商品房预售房源Item(Item):
    """
    This class was generated by feapder
    command: feapder create -i 不动产登记中心_商品房预售房源
    """

    __table_name__ = "不动产登记中心_商品房预售房源"

    def __init__(self, *args, **kwargs):
        # self.delete_flag = 0
        self.id = None
        self.xmid = None
        self.坐落 = None
        self.工程楼号 = None
        self.工程链接 = None
        self.总套数 = None
        self.总面积 = None
        self.项目id = None
        self.预售许可证 = None
        self.建筑面积 = None
        self.lid = None
