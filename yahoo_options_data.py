# __author__ = 'Administrator'
import json
# import sys
import re
# import urllib
from bs4 import BeautifulSoup


def str2int(st):         # convert string with number and comma into int for later contract sorting
    if ',' in st:
        ind = st.find(',')
        new_st = st[:ind]+st[ind+1:]
    else:
        new_st = st
    num = int(new_st)
    return num


def contractAsJson(filename):
    d = dict()                                  # the dict for creating json
    soup = BeautifulSoup(open(filename), "html.parser")
    current_price = 0                           # initial current price
    for item in soup.find_all(name='span', class_='time_rtq_ticker'):
        current_price = float(item.get_text())  # find the tag span with class 'time_rtq_ticker' and get the price
    d["currPrice"] = current_price            # first item of d with key 'currPrice' and value of current price

    urllist = list()                            # initial the url list
    for item in soup.find_all(name='a', href=re.compile(r'[A-Z]*&m=\d{4}-\d{2}')):
        inde = item['href'].find('&')         # find the item with tag 'a' and href follows that pattern
        urllist.append('http://finance.yahoo.com'+item['href'][0:inde+1]+'amp;'+item['href'][inde+1:])
    d["dateUrls"] = urllist                   # second item of d is a list of url with key 'dateUrls'

    cntrct = list()                             # initial the url contract
    for item in soup.find_all(name='a', href=re.compile(r'[A-Z]*&k=')):  # find Strike items
        dct = dict()                            # initial the dict to store the contract
        dct['Strike'] = str(item.get_text())
        it1 = item.parent.next_sibling.contents[0]   # navigate through the tree to get all the contract information
        sym = it1.get_text()
        sym = str(sym)
        ind1 = sym.find('1')-1
        dct['Symbol'] = sym[:ind1+1]
        dct['Date'] = str(sym[ind1+1:ind1+7])
        ind1 += 7
        dct['Type'] = str(sym[ind1])
        it2 = it1.parent.next_sibling
        dct['Last'] = str(it2.get_text())
        it3 = it2.next_sibling.contents[0]
        dct['Change'] = str(it3.get_text())
        it4 = it3.parent.next_sibling
        dct['Bid'] = str(it4.get_text())
        it5 = it4.next_sibling
        dct['Ask'] = str(it5.get_text())
        it6 = it5.next_sibling
        dct['Vol'] = str(it6.get_text())
        it7 = it6.next_sibling
        dct['Open'] = str(it7.get_text())
        cntrct.append(dct)
        cntrct = sorted(cntrct, key=lambda x: str2int(x["Open"]), reverse=True)    # sort in decreasing order of open
    d["optionQuotes"] = cntrct                  # the last item of d is a list of dictionary containing contract info
    # create json data and return
    jsonQuoteData = json.dumps(d, sort_keys=True, indent=4, separators=(',', ': '))
    return jsonQuoteData
