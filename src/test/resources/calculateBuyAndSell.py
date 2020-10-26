import json

lala = json.load(open('./MarkertOrderResponses_example2.json','r'))

sell = {}
buy = {}

for a in lala:
    if a["typeId"] != 34: continue
    loc = a['locationId']
  
    if a['isBuyOrder']:
        buy[loc] = buy.get(loc,0) if buy.get(loc,0) > a['price'] else a['price']
    else:
        sell[loc] = sell.get(loc,999999999999) if sell.get(loc,999999999999) < a['price'] else a['price']

for key in sell.keys():
    if key in buy:
        print("Location: {}, buy: {} & sell: {}".format(key, buy[key], sell[key]))