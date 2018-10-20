import csv

with open('ServerSide/map_files/submap_1/edgelist.csv', 'rb') as f:
    reader = csv.reader(f)
    your_list = list(reader)

for row in your_list:
    print row

print your_list
