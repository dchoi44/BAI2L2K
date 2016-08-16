#-*- coding: utf-8 -*-
import json
import sys
import requests
import os
reload(sys)
sys.setdefaultencoding('utf-8')

url = "http://143.248.135.150:2221/entity_linking"

for root, dirs, files in os.walk('../temp/el/'):
	for fname in files:
		nl = []
		payload = "{\"text\":\""
		full_fname = os.path.join(root, fname)
		fin = open(full_fname,'r')
		print full_fname
		title = unicode(fin.readline())

		while True:
			line = fin.readline()
			if not line: break
			payload += line.strip()
			nl.append(len(payload) - 9)

		fin.close()

		payload += "\"}"
		result = payload[9:-2]
		data = requests.request("POST", url, data=payload, headers={'Content-Type':'application/json'}).json()

		nlp = nl[-1]
		for datum in data[::-1]:
			start = datum["start_offset"]
			end = datum["end_offset"]
			if start < nlp:
				result = result[:nlp] + '\n' + result[nlp:]
				nl.remove(nlp)
				if len(nl) > 0:
					nlp = nl[-1]
				else:
					nlp = -1
			href = unicode(datum["uri"].split('resource/')[1])
			result = result[:start] + '<a href="' + href + '">' + result[start:end] + '</a>' + result[end:]
			result = result.encode('utf-8')
		print '../temp/l2k/l2k_' + fname
		f = open(unicode('../temp/l2k/l2k_' + fname), 'w')
		f.write(title)
		f.write("\n")
		f.write(result)
		f.close()
