##Pärimine

Tõstke kõik failid, mis on kataloogis src/inheritance/analyser oma 
Github'i reposse (icd0019) kataloogi src/inheritance/analyser.

Veenduge, et kood kompileerub.

Kui valite Idea-s Build menüüst -> Rebuild project peaks tulema teade 
"Compilation completed successfully ..."

1. Paketis inheritance.constructor on mõned klassid. Kasutage klassi Runner 
   ja looge nendest klassidest isendid. Jälgige millised konstruktorid 
   käivituvad. Proovige läbi järgmised juhud:

   new Car();
   
   new Car("123 ABC");

   Looge klass Raven, mis laiendab klassi Bird. Tehke isend vastloodud 
   klassist: new Raven();

2. Paketis inheritance.stack on klass LoggingStack, mis laiendab 
   olemasolevat Java kollektsiooni Stack.
   
   LoggingStack pärib ülemklassilt meetodid push() ja pop(), mille 
   kasutamise näide on klassis LoggingStackTest.java.
   
   Kirjutage need meetodid üle selliselt, et nende väljakutsumisel 
   prinditakse konsooli teade nende meetodite käivitamise kohta. Muus 
   osas peab meetodi käitumine samaks jääma.
   
   Lisage klassile LoggingStack meetod pushAll(), mis lisab mitu argumenti 
   korraga. Nt. pushAll(1, 2, 3) lisab kõik kolm argumeti.
   
   Meetodi deklaratsioon on "public void pushAll(Integer... numbers)".

   "Integer..." tähendab siin 0 kuni mitu Integer tüüpi argumenti.

3. Paketis inheritance.calc on klass PayCalculator, mis arvutab töötatud 
   aja põhjal töötasu. See kalkulaator arvestab ka sellega, et tulumaks 
   on 22% ja võtab selle tasust maha.
   
   Meil oleks lisaks vaja sellist kalkulaatorit, mis tulumaksu maha ei 
   arvutaks. Failis TaxFreePayCalculator.java on selle klassi tegemist 
   alustatud. Kirjutage see lõpuni, et kõik klassis CalculatorTest olevad 
   testid tööle hakkaksid.

   Staatilisi muutujaid üle kirjutada ei saa. Seega peate muutma pisut 
   ka klassi PayCalculator, lisades sinna meetodi, läbi mille maksumäära
   küsite. Selle lisatud meetodi saate hiljem alamklassis üle kirjutada.

4. Paketis inheritance.sender on klassid EmailLetterSender ja SmsLetterSender.
   Need klassid konstrueerivad kirja ja saadavad selle läbi sms-i või email-i.
   
   Nendel klassidel on suur hulk ühist koodi. Tehke nendele ühine abstraktne
   ülemklass, mis sisaldaks ühisosa.

5. NB! Selle ülesande juures on kõige olulisem pärimise korrektne kasutamine 
   ja duplikatsiooni vältimine. Loogika on siin üsna lihtne ja õige vastuse 
   saamine ei ole veel ülesande korrektne lahendamine.

   Meil on olemas andmed müügi kohta. Iga kirje koosneb järgmistest 
   komponentidest:

   - toote identifikaator
   - müügi kuupäev
   - ühe ühiku hind
   - müüdud kogus
   
   Müügi info põhjal soovime arvutada müügi kogutulu ja müügi kogutulu 
   konkreetse toote kohta.
   
   Müügi andmed on koos käibemaksuga aga soovime teada kogutulu ilma 
   käibemaksuta.
   
   Ettevõte tegutseb Eestis, Soomes ja laeval (tax free). Meil oleks vaja
   kolme kalkulaatorit, mis arvestaks vastavas piirkonnas kehtinud käibemaksu 
   määraga müügi hetkel.
   
   Eesti käibemaksu määrad on (kehtivuse algus, protsent)<br>
     01.07.2009 20% <br>
     01.01.2024 22% <br>
     01.07.2025 24% <br>

   Soome käibemaksu määrad on  
     01.06.1994 22.0% <br>
     01.07.2010 23.0% <br>
     01.01.2013 24.0% <br>
     01.09.2024 25.5% <br>
   
   Tax free piirkonnas käibemaks puudub.    
   
   Valmis on tehtud 3 klassi vastavate kalkulaatorite jaoks. Kuna kalkulaatoritel
   saab olema suur hulk ühisosa, siis tehke neile ühine abstraktne ülemklass,
   millesse see ühisosa läheb. Spetsiifilistes klassides peaks olema vaid 
   "protected" meetodid minimaalse koodiga.
   
   Abstraktne ülemklass peab olema suletud (sealed).
   
   Lisage kalkulaatoritesse võimalus kõige müüvamate toodete leidmiseks.

   getTop3PopularItems() - tagastab kolme kõige rohkem ühikud müünud toote 
                           identifikaatorid.

   getLargestTotalSalesAmountForSingleItem() - tagastab kõige suurema müügituluga 
                                         toote müügisumma (arvestamata käibemaksu).   

   Koodi testimiseks on klass SalesAnalyserTest.
   
   Selle ülesande kohta on ka koodi ülevaatuse võimalus. Kui esitate lahenduse 
   tähtajaks, siis tähtaja möödudes on teil võimalik kellegi teise tudengi 
   tööd hinnata ja selle eest punkte saada. 
   
   Ülevaatuse leiate pärast tähtaja möödumist hindamissüsteemist ja selle 
   esitamiseks on aega 7 päeva.

   Ülevaatusel tuleb vastata kahele küsimusele.
     - Kas spetsiifilistes klassides on vaid minimaalselt vajalik kood?
     - Kas ülemklass on suletud (sealed)?
   
   Aadressil https://github.com/mkalmo2/icd0019app on rakendus, mis illustreerib,
   kuidas loodud koodi praktikas kasutada. Rakenduse kasutamiseks kopeerige
   oma kood icd0019app projekti ja käivitage main() meetod klassis server.Server.
   See käivitab lokaalse veebiserveri aadressil http://localhost:8080.
   Sellelt aadressilt saate läbi brauseri tulemust vaadata.
   
6. Veenduge, et testid lähevad läbi.
    
   Commit-ige muudatused ja push-ige need Github'i. 
    
   Lisage commit-ile tag ex06. 
    
   Veenduge tulemuste lehelt, et tulemus on positiivne. 

7. See ülesanne pole kohustuslik aga selle lahendamise eest on võimalik 
   lisapunkte saada.

   Paketis bonus.pager on klass SimplePager, mis näitab sellele 
   sisendiks antud täisarve lehekülgede kaupa. See klass on valmis ja 
   seda pole vaja muuta.
   
   Teie ülesanne on kirjutada lõpuni klass FilteringPager, mis kasutab 
   sisendi saamiseks SimplePager klassi aga filtreerib selle väljundist 
   välja null väärtused.
   
   FilteringPager klassi loomisel antakse ette SimplePager klassi objekt, 
   kust andmeid võtta ja lehekülje suurus ehk mitu elementi korraga väljastada.
   Meetodid getNextPage(), getCurrentPage() ja getPreviousPage() väljastavad 
   SimplePager klassi väljundist loetud andmeid, millest on null väärtused 
   eemaldatud.
      
   Kui saame hoida kogu sisendit mälus, siis on ülesanne lihtne ja see 
   lahendus on klassis FilteringPagerWithMemory olemas. Teie ülesanne on 
   lahendada probleem nii, et andmeid mälus ei hoita. Mälus võib hoida 
   ainult kuni kolme täisarv tüüpi muutujat. 
   Seega saame hoida infot nt. selle kohta kui palju infot on sisendist loetud.
   
   Töö testimiseks on klass FilteringPagerTests.
   
   Commit-ige muudatused ja push-ige need Github'i.
   
   Lisage commit-ile tag b1.

Seletused (1-5) ja lahendused (1-4): https://youtu.be/7fmLSfHO7b4
