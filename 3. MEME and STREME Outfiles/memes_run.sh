#!/bin/bash

# Directory where output files will be stored
output_dir1="hm03"
output_dir2="yst04r"
output_dir3="yst08r"

# Check if the output directory exists, if not, create it
if [ ! -d "$output_dir1" ]; then
  mkdir -p "$output_dir1"
fi
if [ ! -d "$output_dir2" ]; then
  mkdir -p "$output_dir2"
fi
if [ ! -d "$output_dir3" ]; then
  mkdir -p "$output_dir3"
fi

outfile_counter=10

for w in {8..16}; do
  ./meme hm03.fasta -dna -w $w -text > "${output_dir1}/out${outfile_counter}.txt"
  ((outfile_counter++))
done

outfile_counter=10

for w in {8..16}; do
  ./meme yst04r.fasta -dna -w $w -text > "${output_dir2}/out${outfile_counter}.txt"
  ((outfile_counter++))
done

outfile_counter=10

for w in {8..16}; do
  ./meme yst08r.fasta -dna -w $w -text > "${output_dir3}/out${outfile_counter}.txt"
  ((outfile_counter++))
done