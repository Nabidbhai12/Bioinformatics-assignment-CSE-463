# Introduction
The tools we are to use for motif search are: MEME and STREME from MEME suite. We have configured MEME suite in our local computer for optimal speed, as it is open-source. We have designed the commands by test 
running the data on the website and then wrote 2 scripts in local, one to run all datasets with MEME, one for STREME.

# Configure
- First we download the source code using this link: https://meme-suite.org/meme/meme-software/5.5.5/meme-5.5.5.tar.gz
- Then in console we use the following commands:
	- ./configure --enable-build-libxml2 --enable-build-libxslt
	- make
	- make test
	- make install

# Process Inputs Using Meme and Streme
- Goto /home/bin. The rest of our operations will be conducted here. 
- First we run the stremes_run.sh file. It takes 3 input datasets and finds the highest scoring motifs with width 8 to 16 from them and prints them in designated outfiles.
	- generalized command: ./streme --p <FASTA format input file> -nmotifs 1 -minw $w -maxw $w -thresh 0.05 -dna anr -text > <outfile>
- Then we run memes_run.sh file. It has similar purpose. 
	- generalized command: ./meme <FASTA format input file> -dna -w $w -text > <outfile>

- For each input folder, out1.txt to out9.txt are STREME outputs, and out10.txt to out18.txt are MEME outputs.

# Process Inputs Using Randomized Motif Search and Gibbs Sampler
- Copy Main.java in ./Codes to /home/bin, then compile and run it. An output.txt file will be created, containing the highest scoring motif of each length for each input.
- ()

# Compare Outputs
## Meme vs Streme
- We record the meme and streme outputs for each input in an excel file, in separate sheets. 

## Meme vs Streme vs Randomized Motif Search vs Gibbs Sampler
- We record the randomized, Gibbs, meme and streme outputs for each input in an excel file, in separate sheets.