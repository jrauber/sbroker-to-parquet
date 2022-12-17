# sbroker-to-parquet

While consolidating a SBroker online trading depot and a DBK broker depot to Parquet (a performance tool for depot analysis) I stumbled accross multiple problems.

- The import of the SBroker PDFs seems to not work very well, many data points were missing.
- Only ten years of data is stored, old data is discarded.
- Switching between two broker is a lossy process, some overview on the performance of the depot is lost.
- There are data issues, like renaming of funds and merging of funds.
- There are share splits. Exp. Ceconomy/Metro

All in all I was not happy how the PDF scraping worked. So this tool transforms the CSV export into the parquet format that can be imported. 
In my case I used SBroker from 2009 to 2020 and then moved the depot to DKB. 

Inputs: 

- SBroker.csv (Export of the trade ledger)
- SBroker-manualCorrection.csv (Manual entry of lost data due to ten year data retention policy)
- DKB.csv ("Tranfer in" of the share values which were moved from SBroker to DKB)

The process is build upon spring batch and served me well during the the data migration.

Disclaimer: This is a quick and *very* dirty implementation, don't expect much.
