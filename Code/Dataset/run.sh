#!/bin/bash
rm "output.txt"
files=("hm03.txt" "yst04r.txt" "yst08r.txt")
for file in "${files[@]}"
do
   printf "%60s %40s\n" "" "$file" >> output.txt
   # Parameter iteration from 8 to 16
   for i in {8..16}
   do
      # Execute Python script with current file and parameter
      python3 RandomizedMotifSearch.py "$file" "$i"
   done
done
