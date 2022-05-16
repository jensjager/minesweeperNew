# Minesweeper
Autorid: Taavi Eistre, Jens Jäger

Tegime oma objektorienteeritud programmeerimise rühmatööks
klassikalise mängu "Minesweeper".

Mängijale on ette antud varjatud väli, kus osad ruudud on "miinid".
Mängija ülesandeks on nähtavaks muuta kõik tühjad ruudud.
Abiks tuleb fakt, et tühjad ruudud näitavad, mitut miini nad puudutavad.
Juhul, kui mängija valib miini, osutub mäng kaotatuks. Samuti saab mängija
potentsiaalseid miine lipuga ära märkida, et need ei ununeks. (Lippe saab parema klõpsuga lisada/eemaldada)

Klassid:
- Main - Main meetodiga klass, kus paikneb jooksev mäng.
- Stopper - Klass, mis loeb mängu võitmiseks aega ning loeb/kirjutab vajadusel saadud andmed faili.
- Ruudud - Ülemklass Miinile ja TühiRuudule, kus on defineeritud vajalikud
isendiväljad ning isendimeetodid.
- Miin - Ruudud alamklass, kus on defineeritud konstruktor ning ülekattega toString(),
mis on vajalik väljal esineva isendi näitamiseks.
- TühiRuut - Sarnane klassile Miin.
- Väli - Klass, mille sees hoitakse Ruudud[][] massiivi ning mis sisaldab väljaga tegutsemiseks
vajalikke meetodeid. Näiteks välja jaoks loomise meetod ning tühjade ruutude
väärtustamise (näitab mitut miini tühi ruut puutub) meetod.
- ValeNumberErind - Erindiklass, mida kasutatakse kasutajasisendi kontrolliks

Tegemise protsess:
1. Osa
   1) Algne koosistumine, mille käigus mõtlesime välja üldise struktuuri ning kuidas peaksid klassid väli,
   miin ja tühimiin töötama.
   2) Mainitud klasside kirjutamine ning testimine
   3) Taavi täiustas struktuurselt neid ning muutis mängu juba mängitavaks.
   4) Jens täiustas väli klassi paari kasuliku meetodiga (näiteks avaldaTühjad()).
   5) Taavi töötles struktuuri natuke ümber loenguvideotest õpitu alusel.
2. Osa
   1) Rühmatöö teiseks osaks koostasime uue javafx projekti.
   2) Lisasime uued vajaminevad klassid ning eemaldasime mittevajalikud.
   3) Tegelesime koos graafilise poole struktureerimisega.
   4) Lisasime Stopper klassiga parima tulemuse süsteemi.
   5) Lõpetuseks lisasime veel võimaluse potentsiaalseid miine lipuga märkida.

Ajakulu:
- Taavi ~ 10h
- Jens ~ 8h

Tegemise mured:
1. Osa:
   - Puuduste poole pealt tooks välja võibolla selle, et enne ülem- ja alamklasside loenguvideoid oli meie
   kood üpriski segane. Kui oleksime nendest omadustest varem teadnud, oleksime saanud juba algusest peale puhtamat
   koodi kirjutada.
2. Osa:
   - Javafx kasutamine/õppimine oli üpriski kulukas ning keeruline.

Hinnang:
- Arvame, et rühmatöö tuli väga hästi välja. Programm jookseb sujuvalt ja näeb meie arvates
ilus välja.
- Eriti oleme uhked Stopper klassi ja selle realiseerimise üle.

Testimine:
- Lisasime järk-järgul funktsioone nii, et lisamiste vahel katsetasime nende töökorrasust.
- Lõpus katsetasime ka tervet programmi mitmete erinevate sisendite/võimalustega
ning kontrollisime, et ka erindid töötaksid.