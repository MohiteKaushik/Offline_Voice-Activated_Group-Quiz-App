package com.example.quizz.utils

import com.example.quizz.models.Hero
import com.example.quizz.models.Movie

class HeroDatabase {

    private val heroes = mutableListOf<Hero>()

    init {
        loadHeroes()
    }

    private fun loadHeroes() {
        heroes.addAll(listOf(
            Hero(
                name = "Mahesh Babu",
                imageResourceName = "hero_mahesh_babu",
                aliases = listOf("mahesh babu", "mahesh", "superstar mahesh"),
                movies = listOf(
                    Movie("Rajakumarudu", 1999, listOf("rajakumarudu")),
                    Movie("Murari", 2001, listOf("murari")),
                    Movie("Okkadu", 2003, listOf("okkadu", "okadu")),
                    Movie("Athadu", 2005, listOf("athadu", "atadu", "attadu")),
                    Movie("Pokiri", 2006, listOf("pokiri", "pokkiri")),
                    Movie("Dookudu", 2011, listOf("dookudu", "dokudu")),
                    Movie("Businessman", 2012, listOf("businessman", "business man")),
                    Movie("Seethamma Vakitlo Sirimalle Chettu", 2013, listOf("svsc", "seethamma vakitlo", "seethamma")),
                    Movie("1 Nenokkadine", 2014, listOf("1 nenokkadine", "one nenokkadine", "nenokkadine")),
                    Movie("Srimanthudu", 2015, listOf("srimanthudu", "srimantudu")),
                    Movie("Bharat Ane Nenu", 2018, listOf("bharat ane nenu", "ban", "bharath ane nenu")),
                    Movie("Maharshi", 2019, listOf("maharshi", "maharishi")),
                    Movie("Sarileru Neekevvaru", 2020, listOf("sarileru neekevvaru", "sarileru")),
                    Movie("Sarkaru Vaari Paata", 2022, listOf("sarkaru vaari paata", "svp"))
                )
            ),

            Hero(
                name = "Prabhas",
                imageResourceName = "hero_prabhas",
                aliases = listOf("prabhas", "rebel star", "young rebel star"),
                movies = listOf(
                    Movie("Eeswar", 2002, listOf("eeswar")),
                    Movie("Varsham", 2004, listOf("varsham", "vaarsham")),
                    Movie("Chatrapathi", 2005, listOf("chatrapathi", "chatrapati")),
                    Movie("Chakram", 2005, listOf("chakram")),
                    Movie("Yogi", 2007, listOf("yogi")),
                    Movie("Bujjigadu", 2008, listOf("bujjigadu")),
                    Movie("Ek Niranjan", 2009, listOf("ek niranjan")),
                    Movie("Billa", 2009, listOf("billa")),
                    Movie("Darling", 2010, listOf("darling")),
                    Movie("Mr. Perfect", 2011, listOf("mr perfect", "mister perfect", "mr. perfect")),
                    Movie("Mirchi", 2013, listOf("mirchi")),
                    Movie("Baahubali", 2015, listOf("baahubali", "bahubali", "baahubali 1")),
                    Movie("Baahubali 2", 2017, listOf("baahubali 2", "bahubali 2", "baahubali two")),
                    Movie("Saaho", 2019, listOf("saaho", "saho")),
                    Movie("Radhe Shyam", 2022, listOf("radhe shyam","radhe","shyam")),
                    Movie("Aadhipurush", 2023, listOf("Aadhipurush","purush","pu")),
                    Movie("Salaar", 2023, listOf("Salaar","salaa")),
                    Movie("Kalki 2898AD", 2024, listOf("kalki","kaalki"))
                )
            ),

            Hero(
                name = "Allu Arjun",
                imageResourceName = "hero_allu_arjun",
                aliases = listOf("allu arjun", "bunny", "stylish star"),
                movies = listOf(
                    Movie("Gangotri", 2003, listOf("gangotri")),
                    Movie("Arya", 2004, listOf("arya", "aarya")),
                    Movie("Bunny", 2005, listOf("bunny")),
                    Movie("Desamuduru", 2007, listOf("desamuduru", "desamudru")),
                    Movie("Parugu", 2008, listOf("parugu")),
                    Movie("Arya 2", 2009, listOf("arya 2", "arya two", "aarya 2")),
                    Movie("Vedam", 2010, listOf("vedam")),
                    Movie("Julayi", 2012, listOf("julayi", "julai")),
                    Movie("Iddarammayilatho", 2013, listOf("iddarammayilatho")),
                    Movie("Race Gurram", 2014, listOf("race gurram", "racegurram")),
                    Movie("S/O Satyamurthy", 2015, listOf("son of satyamurthy", "satyamurthy", "s o satyamurthy")),
                    Movie("Sarrainodu", 2016, listOf("sarrainodu", "sarainodu")),
                    Movie("Duvvada Jagannadham", 2017, listOf("duvvada jagannadham", "dj")),
                    Movie("Ala Vaikunthapurramuloo", 2020, listOf("ala vaikunthapurramuloo", "avpl")),
                    Movie("Pushpa", 2021, listOf("pushpa", "pushpa 1"))
                )
            ),

            Hero(
                name = "Ram Charan",
                imageResourceName = "hero_ram_charan",
                aliases = listOf("ram charan", "charan", "mega power star"),
                movies = listOf(
                    Movie("Chirutha", 2007, listOf("chirutha", "chiruta")),
                    Movie("Magadheera", 2009, listOf("magadheera", "magadeera")),
                    Movie("Orange", 2010, listOf("orange")),
                    Movie("Racha", 2012, listOf("racha","raksha")),
                    Movie("Naayak", 2013, listOf("naayak", "nayak")),
                    Movie("Yevadu", 2014, listOf("yevadu", "evadu")),
                    Movie("Govindudu Andarivadele", 2014, listOf("govindudu andarivadele", "gav","govindudu andari","govindudu","andarivadele")),
                    Movie("Dhruva", 2016, listOf("dhruva", "dhruv")),
                    Movie("Rangasthalam", 2018, listOf("rangasthalam", "ranga sthalam")),
                    Movie("Vinaya Vidheya Rama", 2019, listOf("vinaya vidheya rama", "vvr")),
                    Movie("RRR", 2022, listOf("rrr", "rrrr", "triple r"))
                )
            ),

            Hero(
                name = "Jr NTR",
                imageResourceName = "hero_jr_ntr",
                aliases = listOf("jr ntr", "ntr", "tarak", "young tiger"),
                movies = listOf(
                    Movie("Bala Ramayanam", 1997, listOf("bala ramayanam")),
                    Movie("Ninnu Choodalani", 2001, listOf("ninnu choodalani")),
                    Movie("Student No. 1", 2001, listOf("student no 1", "student number 1")),
                    Movie("Subbu", 2001, listOf("subbu")),
                    Movie("Aadi", 2002, listOf("aadi", "adhi")),
                    Movie("Allari Ramudu", 2002, listOf("allari ramudu")),
                    Movie("Naaga", 2003, listOf("naaga")),
                    Movie("Simhadri", 2003, listOf("simhadri", "simadri")),
                    Movie("Andhrawala", 2004, listOf("andhrawala")),
                    Movie("Samba", 2004, listOf("samba")),
                    Movie("Naa Alludu", 2005, listOf("naa alludu")),
                    Movie("Narasimhudu", 2005, listOf("narasimhudu")),
                    Movie("Ashok", 2006, listOf("ashok")),
                    Movie("Rakhi", 2006, listOf("rakhi")),
                    Movie("Yamadonga", 2007, listOf("yamadonga", "yama donga")),
                    Movie("Kantri", 2008, listOf("kantri")),
                    Movie("Adhurs", 2010, listOf("adhurs", "adurs")),
                    Movie("Brindavanam", 2010, listOf("brindavanam", "brindavan")),
                    Movie("Shakti", 2011, listOf("shakti")),
                    Movie("Oosaravelli", 2011, listOf("oosaravelli")),
                    Movie("Dammu", 2012, listOf("dammu")),
                    Movie("Baadshah", 2013, listOf("baadshah")),
                    Movie("Ramayya Vastavayya", 2013, listOf("ramayya vastavayya")),
                    Movie("Rabhasa", 2014, listOf("rabhasa")),
                    Movie("Temper", 2015, listOf("temper")),
                    Movie("Nannaku Prematho", 2016, listOf("nannaku prematho", "nannaku premato")),
                    Movie("Janatha Garage", 2016, listOf("janatha garage", "janata garage")),
                    Movie("Jai Lava Kusa", 2017, listOf("jai lava kusa", "jlk")),
                    Movie("Aravinda Sametha Veera Raghava", 2018, listOf("aravinda sametha")),
                    Movie("RRR", 2022, listOf("rrr", "rrrr", "triple r")),
                    Movie("Devara: Part 1", 2024, listOf("devara", "devra"))
                )
            ),

            Hero(
                name = "chiranjeevi",
                imageResourceName = "hero_chiranjeevi",
                aliases = listOf("chiranjeevi", "chiru", "megastar"),
                movies = listOf(
                    Movie("Khaidi", 1983, listOf("khaidi", "kaidi")),
                    Movie("Pasivadi Pranam", 1987, listOf("pasivadi pranam")),
                    Movie("Yamudiki Mogudu", 1988, listOf("yamudiki mogudu")),
                    Movie("Jagadeka Veerudu Athiloka Sundari", 1990, listOf("jagadeka veerudu", "jvas")),
                    Movie("Gang Leader", 1991, listOf("gang leader", "gangleader")),
                    Movie("Gharana Mogudu", 1992, listOf("gharana mogudu")),
                    Movie("Mutha Mestri", 1993, listOf("mutha mestri")),
                    Movie("Hitler", 1997, listOf("hitler")),
                    Movie("Indra", 2002, listOf("indra")),
                    Movie("Tagore", 2003, listOf("tagore")),
                    Movie("Shankar Dada M.B.B.S.", 2004, listOf("shankar dada mbbs")),
                    Movie("Khaidi No. 150", 2017, listOf("khaidi no 150", "khaidi number 150")),
                    Movie("Sye Raa Narasimha Reddy", 2019, listOf("sye raa", "saira")),
                    Movie("Godfather", 2022, listOf("godfather"))
                )
            ),
            Hero(
                name = "Pawan Kalyan",
                imageResourceName = "hero_pawan_kalyan",
                aliases = listOf("pawan kalyan", "pk", "power star"),
                movies = listOf(
                    Movie("Akkada Ammayi Ikkada Abbayi", 1996, listOf("aaia")),
                    Movie("Gokulamlo Seetha", 1997, listOf("gokulamlo seetha")),
                    Movie("Suswagatham", 1998, listOf("suswagatham")),
                    Movie("Tholi Prema", 1998, listOf("tholi prema", "toli prema", "toliprema")),
                    Movie("Thammudu", 1999, listOf("thammudu")),
                    Movie("Badri", 2000, listOf("badri")),
                    Movie("Kushi", 2001, listOf("kushi", "kushii")),
                    Movie("Johnny", 2003, listOf("johnny")),
                    Movie("Gudumba Shankar", 2004, listOf("gudumba shankar")),
                    Movie("Balu ABCDEFG", 2005, listOf("balu")),
                    Movie("Bangaram", 2006, listOf("bangaram")),
                    Movie("Annavaram", 2006, listOf("annavaram")),
                    Movie("Jalsa", 2008, listOf("jalsa")),
                    Movie("Puli", 2010, listOf("komaram puli", "puli")),
                    Movie("Teen Maar", 2011, listOf("teen maar")),
                    Movie("Panjaa", 2011, listOf("panjaa")),
                    Movie("Gabbar Singh", 2012, listOf("gabbar singh", "gabbar")),
                    Movie("Cameraman Gangatho Rambabu", 2012, listOf("cmgr")),
                    Movie("Attarintiki Daredi", 2013, listOf("attarintiki daredi", "atharintiki daredi", "ad")),
                    Movie("Gopala Gopala", 2015, listOf("gopala gopala")),
                    Movie("Sardar Gabbar Singh", 2016, listOf("sgs")),
                    Movie("Katamarayudu", 2017, listOf("katamarayudu")),
                    Movie("Agnyaathavaasi", 2018, listOf("agnyaathavaasi")),
                    Movie("Vakeel Saab", 2021, listOf("vakeel saab", "vakil saab")),
                    Movie("Bheemla Nayak", 2022, listOf("bheemla nayak", "bhimla nayak", "bheemla")),
                    Movie("Bro", 2023, listOf("bro"))
                )
            ),
            Hero(
                name = "Nani",
                imageResourceName = "hero_nani",
                aliases = listOf("nani", "natural star"),
                movies = listOf(
                    Movie("Ashta Chamma", 2008, listOf("ashta chamma")),
                    Movie("Ride", 2009, listOf("ride")),
                    Movie("Snehituda", 2009, listOf("snehituda")),
                    Movie("Bheemili Kabaddi Jattu", 2010, listOf("bheemili kabaddi jattu")),
                    Movie("Ala Modalaindi", 2011, listOf("ala modalaindi")),
                    Movie("Pilla Zamindar", 2011, listOf("pilla zamindar")),
                    Movie("Eega", 2012, listOf("eega", "eege", "the fly")),
                    Movie("Yeto Vellipoyindhi Manasu", 2012, listOf("yeto vellipoyindhi manasu")),
                    Movie("Paisa", 2014, listOf("paisa")),
                    Movie("Aaha Kalyanam", 2014, listOf("aaha kalyanam")),
                    Movie("Janda Pai Kapiraju", 2015, listOf("janda pai kapiraju")),
                    Movie("Yevade Subramanyam", 2015, listOf("yevade subramanyam", "yevade")),
                    Movie("Bhale Bhale Magadivoy", 2015, listOf("bhale bhale magadivoy", "bbm")),
                    Movie("Krishna Gaadi Veera Prema Gaadha", 2016, listOf("krishna gaadi veera prema gaadha", "kgvpg")),
                    Movie("Gentleman", 2016, listOf("gentleman")),
                    Movie("Majnu", 2016, listOf("majnu")),
                    Movie("Nenu Local", 2017, listOf("nenu local", "nenu")),
                    Movie("Ninnu Kori", 2017, listOf("ninnu kori")),
                    Movie("Middle Class Abbayi", 2017, listOf("middle class abbayi", "mca")),
                    Movie("Krishnarjuna Yudham", 2018, listOf("krishnarjuna yudham")),
                    Movie("Devadas", 2018, listOf("devadas")),
                    Movie("Jersey", 2019, listOf("jersey")),
                    Movie("V", 2020, listOf("v")),
                    Movie("Tuck Jagadish", 2021, listOf("tuck jagadish")),
                    Movie("Shyam Singha Roy", 2021, listOf("shyam singha roy", "ssr")),
                    Movie("Ante Sundaraniki", 2022, listOf("ante sundaraniki")),
                    Movie("Dasara", 2023, listOf("dasara"))
                )
            ),
            Hero(
                name = "Vijay Deverakonda",
                imageResourceName = "hero_vijay_deverakonda",
                aliases = listOf("vijay deverakonda", "vd", "rowdy star","vijay","devarakonda","vijay devarakonda"),
                movies = listOf(
                    Movie("Nuvvila", 2011, listOf("nuvvila")),
                    Movie("Life Is Beautiful", 2012, listOf("life is beautiful")),
                    Movie("Yevade Subramanyam", 2015, listOf("yevade subramanyam")),
                    Movie("Pelli Choopulu", 2016, listOf("pelli choopulu", "pellichupulu")),
                    Movie("Dwaraka", 2017, listOf("dwaraka")),
                    Movie("Arjun Reddy", 2017, listOf("arjun reddy")),
                    Movie("Mahanati", 2018, listOf("mahanati")),
                    Movie("Ye Mantram Vesave", 2018, listOf("ye mantram vesave")),
                    Movie("Geetha Govindam", 2018, listOf("geetha govindam", "gg")),
                    Movie("NOTA", 2018, listOf("nota")),
                    Movie("Taxiwala", 2018, listOf("taxiwala", "taxi wala")),
                    Movie("Dear Comrade", 2019, listOf("dear comrade")),
                    Movie("World Famous Lover", 2020, listOf("world famous lover", "wfl")),
                    Movie("Liger", 2022, listOf("liger")),
                    Movie("Kushi", 2023, listOf("kushi")),
                    Movie("Family Star", 2024, listOf("family star"))
                )
            ),
            Hero(
                name = "Ravi Teja",
                imageResourceName = "hero_ravi_teja",
                aliases = listOf("ravi teja", "mass maharaja", "mass maharaj"),
                movies = listOf(
                    Movie("Karthavyam", 1990, listOf("karthavyam")), // Minor role
                    Movie("Chaitanya", 1991, listOf("chaitanya")), // Minor role
                    Movie("Sindhooram", 1997, listOf("sindhooram")),
                    Movie("Nee Kosam", 1999, listOf("nee kosam")),
                    Movie("Pardesi", 1999, listOf("pardesi")),
                    Movie("Itlu Sravani Subramanyam", 2001, listOf("itlu sravani subramanyam", "iss")),
                    Movie("Idiot", 2002, listOf("idiot")),
                    Movie("Khadgam", 2002, listOf("khadgam")),
                    Movie("Avunu Valliddaru Ista Paddaru", 2002, listOf("avunu valliddaru")),
                    Movie("Eee Abbai Chala Manchodu", 2003, listOf("eecm")),
                    Movie("Amma Nanna O Tamila Ammayi", 2003, listOf("anota")),
                    Movie("Dongodu", 2003, listOf("dongodu")),
                    Movie("Venky", 2004, listOf("venky")),
                    Movie("Naa Autograph", 2004, listOf("naa autograph")),
                    Movie("Bhadra", 2005, listOf("bhadra")),
                    Movie("Chanti", 2006, listOf("chanti")),
                    Movie("Vikramarkudu", 2006, listOf("vikramarkudu")),
                    Movie("Dubai Seenu", 2007, listOf("dubai seenu")),
                    Movie("Krishna", 2008, listOf("krishna")),
                    Movie("Kick", 2009, listOf("kick")),
                    Movie("Shambo Shiva Shambo", 2010, listOf("shambo shiva shambo")),
                    Movie("Mirapakay", 2011, listOf("mirapakay")),
                    Movie("Don Seenu", 2011, listOf("don seenu")),
                    Movie("Balupu", 2013, listOf("balupu")),
                    Movie("Power", 2014, listOf("power")),
                    Movie("Bengal Tiger", 2015, listOf("bengal tiger")),
                    Movie("Raja The Great", 2017, listOf("raja the great", "rtg")),
                    Movie("Touch Chesi Chudu", 2018, listOf("touch chesi chudu")),
                    Movie("Disco Raja", 2020, listOf("disco raja")),
                    Movie("Krack", 2021, listOf("krack")),
                    Movie("Khiladi", 2022, listOf("khiladi")),
                    Movie("Dhamaka", 2022, listOf("dhamaka")),
                    Movie("Waltair Veerayya", 2023, listOf("waltair veerayya")),
                    Movie("Ravanasura", 2023, listOf("ravanasura")),
                    Movie("Eagle", 2024, listOf("eagle"))
                )
            ),

                    Hero(
                    name = "Ram Pothineni",
            imageResourceName = "hero_ram_pothineni",
            aliases = listOf("ram pothineni", "ram", "energetic star"),
            movies = listOf(
                Movie("Devadasu", 2006, listOf("devadasu")),
                Movie("Jagadam", 2007, listOf("jagadam")),
                Movie("Ready", 2008, listOf("ready")),
                Movie("Maska", 2009, listOf("maska")),
                Movie("Ganesh Just Ganesh", 2009, listOf("ganesh just ganesh")),
                Movie("Rama Rama Krishna Krishna", 2010, listOf("rrkk")),
                Movie("Kandireega", 2011, listOf("kandireega")),
                Movie("Endukante Premanta", 2012, listOf("endukante premanta")),
                Movie("Ongole Githa", 2013, listOf("ongole githa")),
                Movie("Masala", 2013, listOf("masala")),
                Movie("Pandaga Chesko", 2015, listOf("pandaga chesko")),
                Movie("Shivam", 2015, listOf("shivam")),
                Movie("Nenu Sailaja", 2016, listOf("nenu sailaja")),
                Movie("Hyper", 2016, listOf("hyper")),
                Movie("Vunnadi Okate Zindagi", 2017, listOf("vunnadi okate zindagi")),
                Movie("Hello Guru Prema Kosame", 2018, listOf("hello guru prema kosame")),
                Movie("iSmart Shankar", 2019, listOf("ismart shankar")),
                Movie("Red", 2021, listOf("red")),
                Movie("The Warriorr", 2022, listOf("the warriorr")),
                Movie("Skanda", 2023, listOf("skanda"))
            )
        ),

            Hero(
                name = "Allari Naresh",
                imageResourceName = "hero_allari_naresh",
                aliases = listOf("allari naresh", "naresh", "allari"),
                movies = listOf(
                    Movie("Allari", 2002, listOf("allari")),
                    Movie("Dhana 51", 2005, listOf("dhana 51")),
                    Movie("Nenu", 2004, listOf("nenu")),
                    Movie("Gamyam", 2008, listOf("gamyam")),
                    Movie("Sudigadu", 2012, listOf("sudigadu")),
                    Movie("Shambho Shiva Shambho", 2010, listOf("shambho shiva shambho")),
                    Movie("Kithakithalu", 2006, listOf("kithakithalu")),
                    Movie("Seema Sastri", 2007, listOf("seema sastri")),
                    Movie("Blade Babji", 2008, listOf("blade babji")),
                    Movie("Fitting Master", 2009, listOf("fitting master")),
                    Movie("Betting Bangaraju", 2010, listOf("betting bangaraju")),
                    Movie("Aha Naa Pellanta", 2011, listOf("aha naa pellanta")),
                    Movie("Jump Jilani", 2014, listOf("jump jilani")),
                    Movie("Maharshi", 2019, listOf("maharshi")),
                    Movie("Naandhi", 2021, listOf("naandhi")),
                    Movie("Itlu Maredumilli Prajaneekam", 2022, listOf("itlu maredumilli")),
                    Movie("Ugram", 2023, listOf("ugram"))
                )
            ),
            Hero(
                name = "Sai Dharam Tej",
                imageResourceName = "hero_sai_dharam_tej",
                aliases = listOf("sai dharam tej", "sdt", "supreme hero"),
                movies = listOf(
                    Movie("Pilla Nuvvu Leni Jeevitham", 2014, listOf("pilla nuvvu leni jeevitham", "pnlk")),
                    Movie("Subramanyam for Sale", 2015, listOf("subramanyam for sale", "sfs")),
                    Movie("Supreme", 2016, listOf("supreme")),
                    Movie("Thikka", 2016, listOf("thikka")),
                    Movie("Winner", 2017, listOf("winner")),
                    Movie("Jawaan", 2017, listOf("jawaan")),
                    Movie("Inttelligent", 2018, listOf("inttelligent")),
                    Movie("Tej I Love You", 2018, listOf("tej i love you")),
                    Movie("Chitralahari", 2019, listOf("chitralahari")),
                    Movie("Prati Roju Pandage", 2019, listOf("prati roju pandage", "prp")),
                    Movie("Solo Brathuke So Better", 2020, listOf("solo brathuke so better", "sbsb")),
                    Movie("Republic", 2021, listOf("republic")),
                    Movie("Virupaksha", 2023, listOf("virupaksha")),
                    Movie("Bro", 2023, listOf("bro"))
                )
            ),
                    Hero(
                    name = "Nandamuri Balakrishna",
            imageResourceName = "hero_balakrishna",
            aliases = listOf("nandamuri balakrishna", "balakrishna", "nbk", "balayya"),
            movies = listOf(
                // Child Artist / Early Roles
                Movie("Tatamma Kala", 1974, listOf("tatamma kala")),
                Movie("Ram Raheem", 1974, listOf("ram raheem")),
                Movie("Annadammula Anubandham", 1975, listOf("annadammula anubandham")),
                Movie("Daana Veera Soora Karna", 1977, listOf("dvs karna")),
                Movie("Akbar Salim Anarkali", 1979, listOf("akbar salim anarkali")),
                Movie("Rowdy Ramudu Konte Krishnudu", 1980, listOf("rowdy ramudu")),
                Movie("Anuraga Devatha", 1982, listOf("anuraga devatha")),
                // Lead Roles Begin
                Movie("Sahasame Jeevitham", 1984, listOf("sahasame jeevitham")),
                Movie("Mangammagari Manavadu", 1984, listOf("mangammagari manavadu")),
                Movie("Kathanayakudu", 1984, listOf("kathanayakudu")),
                Movie("Pattabhishekam", 1985, listOf("pattabhishekam")),
                Movie("Nippulanti Manishi", 1986, listOf("nippulanti manishi")),
                Movie("Muddula Krishnayya", 1986, listOf("muddula krishnayya")),
                Movie("Desoddarakudu", 1986, listOf("desoddarakudu")),
                Movie("Apoorva Sahodarulu", 1986, listOf("apoorva sahodarulu")),
                Movie("Bhanumati Gari Mogudu", 1987, listOf("bhanumati gari mogudu")),
                Movie("Muvva Gopaludu", 1987, listOf("muvva gopaludu")),
                Movie("Inspector Pratap", 1988, listOf("inspector pratap")),
                Movie("Ramudu Bheemudu", 1988, listOf("ramudu bheemudu")),
                Movie("Raktabhishekam", 1988, listOf("rakthabhishekam")),
                Movie("Lorry Driver", 1990, listOf("lorry driver")),
                Movie("Nari Nari Naduma Murari", 1990, listOf("nari nari naduma murari")),
                Movie("Aditya 369", 1991, listOf("aditya 369")),
                Movie("Rowdy Inspector", 1992, listOf("rowdy inspector")),
                Movie("Bangaru Bullodu", 1993, listOf("bangaru bullodu")),
                Movie("Bhairava Dweepam", 1994, listOf("bhairava dweepam")),
                Movie("Bobbili Simham", 1994, listOf("bobbili simham")),
                Movie("Peddannayya", 1997, listOf("peddannayya")),
                Movie("Samarasimha Reddy", 1999, listOf("samarasimha reddy")),
                Movie("Sultan", 1999, listOf("sultan")),
                Movie("Narasimha Naidu", 2001, listOf("narasimha naidu")),
                Movie("Chennakesava Reddy", 2002, listOf("chennakesava reddy")),
                Movie("Lakshmi Narasimha", 2004, listOf("lakshmi narasimha")),
                Movie("Simha", 2010, listOf("simha")),
                Movie("Sri Rama Rajyam", 2011, listOf("sri rama rajyam")),
                Movie("Legend", 2014, listOf("legend")),
                Movie("Dictator", 2016, listOf("dictator")),
                Movie("Gautamiputra Satakarni", 2017, listOf("gautamiputra satakarni", "gpsk")),
                Movie("Paisa Vasool", 2017, listOf("paisa vasool")),
                Movie("Ruler", 2019, listOf("ruler")),
                Movie("Akhanda", 2021, listOf("akhanda")),
                Movie("Veera Simha Reddy", 2023, listOf("veera simha reddy")),
                Movie("Bhagavanth Kesari", 2023, listOf("bhagavanth kesari"))
            )
        ),
            Hero(
                name = "Nithiin",
                imageResourceName = "hero_nithiin",
                aliases = listOf("nithiin", "nitin"),
                movies = listOf(
                    Movie("Jayam", 2002, listOf("jayam")),
                    Movie("Dil", 2003, listOf("dil")),
                    Movie("Sambaram", 2003, listOf("sambaram")),
                    Movie("Sri Anjaneyam", 2004, listOf("sri anjaneyam")),
                    Movie("Sye", 2004, listOf("sye")),
                    Movie("Dhairyam", 2005, listOf("dhaiyram")),
                    Movie("Allari Bullodu", 2005, listOf("allari bullodu")),
                    Movie("Raam", 2006, listOf("raam")),
                    Movie("Takkari", 2007, listOf("takkari")),
                    Movie("Aatadista", 2008, listOf("aatadista")),
                    Movie("Hero", 2008, listOf("hero")),
                    Movie("Drona", 2009, listOf("drona")),
                    Movie("Rechipo", 2009, listOf("rechipo")),
                    Movie("Seeta Ramula Kalyanam Lankalo", 2010, listOf("srkl")),
                    Movie("Maaro", 2011, listOf("maaro")),
                    Movie("Ishq", 2012, listOf("ishq")),
                    Movie("Gunde Jaari Gallanthayyinde", 2013, listOf("gjgr")),
                    Movie("Heart Attack", 2014, listOf("heart attack")),
                    Movie("Chinnadana Nee Kosam", 2014, listOf("cnk")),
                    Movie("Courier Boy Kalyan", 2015, listOf("courier boy kalyan")),
                    Movie("A Aa", 2016, listOf("a aa")),
                    Movie("LIE", 2017, listOf("lie")),
                    Movie("Chal Mohan Ranga", 2018, listOf("cmr")),
                    Movie("Srinivasa Kalyanam", 2018, listOf("srinivasa kalyanam")),
                    Movie("Bheeshma", 2020, listOf("bheeshma")),
                    Movie("Check", 2021, listOf("check")),
                    Movie("Rang De", 2021, listOf("rang de")),
                    Movie("Maestro", 2021, listOf("maestro")),
                    Movie("Macherla Niyojakavargam", 2022, listOf("macherla niyojakavargam")),
                    Movie("Extra Ordinary Man", 2023, listOf("extra ordinary man")),
                    Movie("Robinhood", 2025, listOf("robinhood")) // Upcoming
                )
            ),
            Hero(
                name = "Varun Tej",
                imageResourceName = "hero_varun_tej",
                aliases = listOf("varun tej", "vt"),
                movies = listOf(
                    Movie("Hands Up", 2000, listOf("hands up")), // Child Actor
                    Movie("Mukunda", 2014, listOf("mukunda")),
                    Movie("Kanche", 2015, listOf("kanche")),
                    Movie("Loafer", 2015, listOf("loafer")),
                    Movie("Mister", 2017, listOf("mister")),
                    Movie("Fidaa", 2017, listOf("fidaa")),
                    Movie("Tholi Prema", 2018, listOf("tholi prema")),
                    Movie("Antariksham 9000 KMPH", 2018, listOf("antariksham")),
                    Movie("F2: Fun and Frustration", 2019, listOf("f2")),
                    Movie("Gaddalakonda Ganesh", 2019, listOf("gaddalakonda ganesh", "valmiki")),
                    Movie("Ghani", 2022, listOf("ghani")),
                    Movie("F3: Fun and Frustration", 2022, listOf("f3")),
                    Movie("Gandeevadhari Arjuna", 2023, listOf("gandeevadhari arjuna")),
                    Movie("Operation Valentine", 2024, listOf("operation valentine")),
                    Movie("Matka", 2024, listOf("matka")) // Upcoming
                )
            ),
            Hero(
                name = "Akkineni Akhil",
                imageResourceName = "hero_akhil_akkineni",
                aliases = listOf("akhil akkineni", "akhil"),
                movies = listOf(
                    Movie("Sisindri", 1995, listOf("sisindri")), // Child Artist
                    Movie("Manam", 2014, listOf("manam")), // Cameo
                    Movie("Akhil", 2015, listOf("akhil", "akhil the power of jua")),
                    Movie("Aatadukundam Raa", 2016, listOf("aatadukundam raa")), // Special Appearance
                    Movie("Hello", 2017, listOf("hello")),
                    Movie("Mr. Majnu", 2019, listOf("mr majnu")),
                    Movie("Most Eligible Bachelor", 2021, listOf("most eligible bachelor", "meb")),
                    Movie("Agent", 2023, listOf("agent"))
                )
            ),
            Hero(
                name = "Adivi Sesh",
                imageResourceName = "hero_adivi_sesh",
                aliases = listOf("adivi sesh", "sesh"),
                movies = listOf(
                    Movie("Sontham", 2002, listOf("sontham")), // Minor Role
                    Movie("Karma", 2010, listOf("karma")),
                    Movie("Panjaa", 2011, listOf("panjaa")),
                    Movie("Balupu", 2013, listOf("balupu")),
                    Movie("Kiss", 2013, listOf("kiss")),
                    Movie("Run Raja Run", 2014, listOf("run raja run")),
                    Movie("Ladies & Gentlemen", 2015, listOf("ladies and gentlemen")),
                    Movie("Baahubali: The Beginning", 2015, listOf("baahubali 1")),
                    Movie("Dongaata", 2015, listOf("dongaata")),
                    Movie("Kshanam", 2016, listOf("kshanam")),
                    Movie("Oopiri", 2016, listOf("oopiri")), // Cameo
                    Movie("Ami Thumi", 2017, listOf("ami thumi")),
                    Movie("Goodachari", 2018, listOf("goodachari")),
                    Movie("Oh! Baby", 2019, listOf("oh baby")), // Cameo
                    Movie("Evaru", 2019, listOf("evaru")),
                    Movie("Major", 2022, listOf("major")),
                    Movie("HIT: The Second Case", 2022, listOf("hit 2")),
                    Movie("Dacoit: A Love Story", 2026, listOf("dacoit")), // Upcoming
                    Movie("G2", 2026, listOf("g2", "goodachari 2")) // Upcoming
                )
            ),

            Hero(
                name = "Siddharth",
                imageResourceName = "hero_siddharth",
                aliases = listOf("siddharth", "siddu", "bommarillu sidhu"),
                movies = listOf(
                    Movie("Nuvvu Naaku Nachav", 2001, listOf("nuvvu naaku nachav")), // Assistant Director / Minor Role
                    Movie("Boys", 2003, listOf("boys")),
                    Movie("Aayutha Ezhuthu", 2004, listOf("aayutha ezhuthu")), // Tamil
                    Movie("Nuvvostanante Nenoddantana", 2005, listOf("nvnv")),
                    Movie("Chukkallo Chandrudu", 2006, listOf("chukkallo chandrudu")),
                    Movie("Bommarillu", 2006, listOf("bommarillu")),
                    Movie("Aata", 2007, listOf("aata")),
                    Movie("Konchem Ishtam Konchem Kashtam", 2009, listOf("kisk")),
                    Movie("Oye!", 2009, listOf("oye")),
                    Movie("Bava", 2010, listOf("bava")),
                    Movie("Anaganaga O Dheerudu", 2011, listOf("anaganaga o dheerudu")),
                    Movie("Oh My Friend", 2011, listOf("oh my friend")),
                    Movie("Love Failure", 2012, listOf("love failure")),
                    Movie("Jabardasth", 2013, listOf("jabardasth")),
                    Movie("Gomala", 2014, listOf("gomala")),
                    Movie("Chikkadu Dorakadu", 2015, listOf("chikkadu dorakadu")),
                    Movie("Gruham", 2017, listOf("gruham")),
                    Movie("Savaari", 2020, listOf("savaari")),
                    Movie("Maha Samudram", 2021, listOf("maha samudram"))
                )
            ),
            Hero(
                name = "Sundeep Kishan",
                imageResourceName = "hero_sundeep_kishan",
                aliases = listOf("sundeep kishan", "sundeep"),
                movies = listOf(
                    Movie("Prasthanam", 2010, listOf("prasthanam")),
                    Movie("Shor In The City", 2011, listOf("shor in the city")),
                    Movie("Sega", 2011, listOf("sega")),
                    Movie("Routine Love Story", 2012, listOf("routine love story")),
                    Movie("Gundello Godari", 2013, listOf("gundello godari")),
                    Movie("Venkatadri Express", 2013, listOf("venkatadri express")),
                    Movie("D for Dopidi", 2013, listOf("d for dopidi")),
                    Movie("Ra Ra Krishnayya", 2014, listOf("ra ra krishnayya")),
                    Movie("Joru", 2014, listOf("joru")),
                    Movie("Beeruva", 2015, listOf("beeruva")),
                    Movie("Tiger", 2015, listOf("tiger")),
                    Movie("Okka Kshanam", 2017, listOf("okka kshanam")),
                    Movie("Ninu Veedani Needanu Nene", 2019, listOf("nvnn")),
                    Movie("Tenali Ramakrishna BA BL", 2019, listOf("tenali ramakrishna")),
                    Movie("A1 Express", 2021, listOf("a1 express")),
                    Movie("Gully Rowdy", 2021, listOf("gully rowdy")),
                    Movie("Michael", 2023, listOf("michael")),
                    Movie("Ooru Peru Bhairavakona", 2024, listOf("opbk"))
                )
            ),
            Hero(
                name = "Nikhil Siddharth",
                imageResourceName = "hero_nikhil_siddharth",
                aliases = listOf("nikhil siddharth", "nikhil"),
                movies = listOf(
                    Movie("Happy Days", 2007, listOf("happy days")),
                    Movie("Ankit, Pallavi & Friends", 2008, listOf("apf")),
                    Movie("Yuvatha", 2008, listOf("yuvatha")),
                    Movie("Veedu Theda", 2011, listOf("veedu theda")),
                    Movie("Swami Ra Ra", 2013, listOf("swami ra ra")),
                    Movie("Karthikeya", 2014, listOf("karthikeya")),
                    Movie("Surya Vs Surya", 2015, listOf("surya vs surya")),
                    Movie("Shankarabharanam", 2015, listOf("shankarabharanam")),
                    Movie("Ekkadiki Pothavu Chinnavada", 2016, listOf("epcv")),
                    Movie("Keshava", 2017, listOf("keshava")),
                    Movie("Kirrak Party", 2018, listOf("kirrak party")),
                    Movie("Arjun Suravaram", 2019, listOf("arjun suravaram")),
                    Movie("Karthikeya 2", 2022, listOf("karthikeya 2")),
                    Movie("18 Pages", 2022, listOf("18 pages")),
                    Movie("Spy", 2023, listOf("spy")),
                    Movie("Swayambhu", 2024, listOf("swayambhu")) // Upcoming
                )
            ),
            Hero(
                name = "Sharwanand",
                imageResourceName = "hero_sharwanand",
                aliases = listOf("sharwanand", "sharwa"),
                movies = listOf(
                    Movie("Aahaa", 1998, listOf("aahaa")), // Minor Role
                    Movie("Yuvasena", 2004, listOf("yuvasena")),
                    Movie("Gowri", 2004, listOf("gowri")),
                    Movie("Sankranthi", 2005, listOf("sankranthi")),
                    Movie("Vennela", 2005, listOf("vennela")),
                    Movie("Amma Cheppindi", 2006, listOf("amma cheppindi")),
                    Movie("Classmates", 2007, listOf("classmates")),
                    Movie("Gamyam", 2008, listOf("gamyam")),
                    Movie("Andari Bandhuvaya", 2010, listOf("andari bandhuvaya")),
                    Movie("Engeyum Eppothum", 2011, listOf("engeyum eppothum")), // Tamil
                    Movie("Journey", 2011, listOf("journey")),
                    Movie("Run Raja Run", 2014, listOf("run raja run")),
                    Movie("Malli Malli Idi Rani Roju", 2015, listOf("mmirr")),
                    Movie("Express Raja", 2016, listOf("express raja")),
                    Movie("Shatamanam Bhavati", 2017, listOf("shatamanam bhavati")),
                    Movie("Radha", 2017, listOf("radha")),
                    Movie("Mahanubhavudu", 2017, listOf("mahanubhavudu")),
                    Movie("Padi Padi Leche Manasu", 2018, listOf("pplm")),
                    Movie("Jaanu", 2020, listOf("jaanu")),
                    Movie("Sreekaram", 2021, listOf("sreekaram")),
                    Movie("Maha Samudram", 2021, listOf("maha samudram")),
                    Movie("Aadavallu Meeku Johaarlu", 2022, listOf("amj")),
                    Movie("Oke Oka Jeevitham", 2022, listOf("ooj")),
                    Movie("Runway", 2023, listOf("runway"))
                )
            ),
            Hero(
                name = "Naga Chaitanya",
                imageResourceName = "hero_naga_chaitanya",
                aliases = listOf("naga chaitanya", "chay", "nc"),
                movies = listOf(
                    Movie("Josh", 2009, listOf("josh")),
                    Movie("Ye Maaya Chesave", 2010, listOf("ymc")),
                    Movie("Bava", 2010, listOf("bava")),
                    Movie("100% Love", 2011, listOf("100 percent love")),
                    Movie("Dhada", 2011, listOf("dhada")),
                    Movie("Bejawada", 2011, listOf("bejawada")),
                    Movie("Autonagar Surya", 2014, listOf("autonagar surya")),
                    Movie("Manam", 2014, listOf("manam")),
                    Movie("Oka Laila Kosam", 2014, listOf("olk")),
                    Movie("Dohchay", 2015, listOf("dohchay")),
                    Movie("Premam", 2016, listOf("premam")),
                    Movie("Sahasam Swasaga Sagipo", 2016, listOf("sss")),
                    Movie("Rarandoi Veduka Chudham", 2017, listOf("rvc")),
                    Movie("Yuddham Sharanam", 2017, listOf("yuddham sharanam")),
                    Movie("Shailaja Reddy Alludu", 2018, listOf("sra")),
                    Movie("Savyasachi", 2018, listOf("savyasachi")),
                    Movie("Majili", 2019, listOf("majili")),
                    Movie("Venky Mama", 2019, listOf("venky mama")),
                    Movie("Love Story", 2021, listOf("love story")),
                    Movie("Thank You", 2022, listOf("thank you")),
                    Movie("Laal Singh Chaddha", 2022, listOf("lsc")), // Hindi
                    Movie("Custody", 2023, listOf("custody")),
                    Movie("Thandel", 2025, listOf("thandel")) // Upcoming
                )
            )



            )
        )
    }

    fun getAllHeroes(): List<Hero> {
        return heroes.shuffled()
    }

    fun getRandomHero(): Hero {
        return heroes.random()
    }

    fun validateMovie(guess: String, currentHero: Hero, usedMovies: Set<String>): Pair<Boolean, Movie?> {
        val normalizedGuess = guess.lowercase().trim()

        // Check if already used
        if (usedMovies.any { it.lowercase() == normalizedGuess }) {
            return Pair(false, null)
        }

        // Check if movie belongs to current hero
        val foundMovie = currentHero.movies.find { movie ->
            movie.variations.any { it == normalizedGuess } ||
                    movie.name.lowercase() == normalizedGuess
        }

        return Pair(foundMovie != null, foundMovie)
    }

    fun validateHero(guess: String, usedHeroes: Set<String>): Pair<Boolean, Hero?> {
        val normalizedGuess = guess.lowercase().trim()

        // Check if already used
        if (usedHeroes.any { it.lowercase() == normalizedGuess }) {
            return Pair(false, null)
        }

        // Find matching hero
        val foundHero = heroes.find { hero ->
            hero.aliases.any { it == normalizedGuess } ||
                    hero.name.lowercase() == normalizedGuess
        }

        return Pair(foundHero != null, foundHero)
    }

    fun getAllHeroNames(): List<String> {
        return heroes.map { it.name }
    }
}