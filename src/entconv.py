#-*- coding: utf-8 -*-
import json
import sys
import requests
import os
reload(sys)
sys.setdefaultencoding('utf-8')

payload = "{\"text\":\""
url = "http://143.248.135.150:2222/entity_linking"

for root, dirs, files in os.walk('../temp/el/'):
	for fname in files:
		full_fname = os.path.join(root, fname)
		fin = open(full_fname,'r')
		print full_fname
		title = unicode(fin.readline())

		while True:
			line = fin.readline()
			if not line: break
			payload += line.strip()

		fin.close()

		payload += "\"}"
		result = payload[9:-2]
		data = requests.request("POST", url, data=payload, headers={'Content-Type':'application/json'}).json()

		for datum in data[::-1]:
			start = datum["offset_start"]
			end = datum["offset_end"]
			href = unicode(datum["uri"].split('resource/')[1])
			result = result[:start] + '<a href="' + href + '">' + result[start:end] + '</a>' + result[end:]
			result = result.encode('utf-8')

		f = open(unicode('../temp/l2k/l2k_.tmp'), 'w')
		f.write(title)
		f.write("\n")
		f.write(result)
		f.close()
		os.remove(full_fname)