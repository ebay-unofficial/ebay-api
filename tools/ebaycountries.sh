echo 'ebaycode,conutry' > ebaycountries.csv
for ((i=1;i<=250;i++));
do 
   country=$(curl "https://www.ebay.de/sch/i.html?&_nkw=hello&_fcid=$i" \
   | grep '<span class="loczipb" id="loczip">Versand nach&nbsp;<a href="javascript:;">' \
   | sed -e 's/<span class="loczipb" id="loczip">Versand nach&nbsp;<a href="javascript:;">//g;s/<\/a>//;s/\t//g')
   echo "$i,$country" >> ebaycountries.csv
done
