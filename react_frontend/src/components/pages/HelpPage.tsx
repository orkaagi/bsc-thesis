import Header from "../common_elements/Header";
import Footer from "../common_elements/Footer";
import { useAuth } from "../authentication/AuthProvider";

export default function HelpBody() {
  const { isAuthenticated, user, token, login, logout } = useAuth();

  return (
    <>
      <Header firstRow="Súgó" secondRow="" currentPage="help" />
      <div className="flex-1 mx-4 md:mx-8 lg:mx-16 px-4 md:px-8 lg:px-16">
        <h2 className="mt-10">A bridzs játék</h2>
        <p className="mt-6 text-md text-gray-700">
          A bridzs egy világszerte ismert kártyajáték és sport, amit hobbi
          szinten vagy versenyszerűen is lehet űzni. A bridzset négy játékos
          játssza, akik a négy égtájon ülnek, és az egymással szemben ülő
          játékosok kooperáló partnerek. A játék elején egy franciakártya pakli
          kerül egyenlően kiosztásra a játékosok között. Majd a játékosok
          információt osztanak meg egymással a lapjaikról a licit során és a
          licit végére az adott felvétel is meghatározásra kerül. A felvétel azt
          mondja meg, hogy melyik játékos lesz a felvevő és adott adu mellett
          hány ütést vállal az összesen megszerezhető tizenháromból. Ezután a
          lejátszás tizenhárom körből áll, egy körbe mindegyik játékos rak egy
          lapot a hívóval kezdve és az óramutató járásával megegyező irányba
          haladva. A kört az viszi, aki a legnagyobb értékű lapot vagy a
          legnagyobb értékű adut teszi, és ő lesz a következő körben a hívó. A
          felvevő célja, hogy minél többet üssön és teljesítse a felvételt, az
          ellenjátékosok célja pedig az, hogy minél többet üssenek és a felvevő
          ne teljesítse a felvételt. <br /> A bridzs játékról bővebben
          olvashatsz a Magyar Bridzs Szövetség honalpján:{" "}
          <a href="https://www.bridzs.hu/hu/mi_a_bridzs">Mi a bridzs?</a>
        </p>

        <h2 className="mt-10">Az alkalmazás használata</h2>
        <p className="mt-6 text-md text-gray-700">
          Az alkalmazást bejelentkezés nélkül is lehet használni. A főoldalra
          belépve azonnal elindul egy instant játék.
          <br />
          A fenti menüben a Bejelentkezés gombra kattintva lehet eljutni a
          bejelentkezés oldalra, ahol a regisztráció során használt
          felhasználónév és jelszó megadásával, majd a Bejelentkezés gombra
          kattintva lehet belépni az alkalmazásba. Miért éri meg bejelentkezni?
          Ha bejelentkezel, akkor az alkalmazás minden végigjátszott parti után
          elmenti annak az eredményét, amit megtekinthetsz az Eredmények
          oldalon. Az Eredmények oldalt bejelentkezés után az Eredmények
          menüpontra kattintva érheted el.
          <br />A fenti menüben az Új játék gombra kattintva tudsz új játékot
          indítani, alapértelmezett paraméterekkel, vagy paraméterek
          megadásával.
        </p>
      </div>

      <Footer></Footer>
    </>
  );
}
