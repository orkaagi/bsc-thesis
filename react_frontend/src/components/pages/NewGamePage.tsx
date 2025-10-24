import { useState, useEffect } from "react";
import Header from "../common_elements/Header";
import Footer from "../common_elements/Footer";

export default function NewGameForm() {
  const [parametersVisible, setParametersVisible] = useState(false);

  const [gameMode, setGameMode] = useState<string>(() => {
    const stored = localStorage.getItem("game_mode");
    return stored === "DEFENSE" || stored === "ANALYSIS" ? stored : "DEFENSE";
  });

  const [declarerLevel, setDeclarerLevel] = useState<string>(() => {
    const stored = localStorage.getItem("declarer_level");
    return stored === "BEGINNER" || stored === "MEDIUM" ? stored : "BEGINNER";
  });

  const [trumpType, setTrumpType] = useState<string>(() => {
    const stored = localStorage.getItem("trump_type");
    return stored === "NOTRUMP" || stored === "COLOR" ? stored : "";
  });

  const handleNewGame = () => {
    if (!parametersVisible) {
      localStorage.setItem("game_mode", "DEFENSE");
      localStorage.setItem("declarer_level", "BEGINNER");
      localStorage.setItem("trump_type", "");
    }
  };

  const handleTrumpChange = (value: string) => {
    setTrumpType((prev) => (prev === value ? "" : value));
  };

  useEffect(() => {
    localStorage.setItem("game_mode", gameMode);
    localStorage.setItem("declarer_level", declarerLevel);
    localStorage.setItem("trump_type", trumpType);
    console.log(
      "localstorage mentések:",
      localStorage.getItem("game_mode"),
      localStorage.getItem("declarer_level"),
      localStorage.getItem("trump_type")
    );
  }, [gameMode, declarerLevel, trumpType]);

  useEffect(() => {
    localStorage.setItem("game_id", "-1");
  }, []);

  const toggleParametersVisibility = () => {
    setParametersVisible((prev) => !prev);
  };

  return (
    <>
      <Header firstRow="Új játék" secondRow="" currentPage="start" />
      <div
        id="main-content"
        className="flex flex-col justify-center items-center gap-4 px-12 py-8 w-full flex-1"
      >
        <div
          id="start-form"
          className="flex flex-col gap-4 bg-white shadow-md rounded px-8 pt-6 pb-4 mt-1 mb-1"
        >
          <a
            id="start-new-game"
            href="index.html"
            className="rounded items-center p-4 text-center shadow transition-all duration-150 ease-in-out active:scale-95"
            onClick={(e) => {
              e.preventDefault();
              handleNewGame();
              window.location.href = "index.html";
            }}
          >
            Játék indítása
          </a>
          {parametersVisible ? (
            <p className="text-gray-700">
              Válszd ki a játékmódot és állítsd be, hogy milyen ügyességi szintű
              felvevő ellen szeretnél játszani.
              <br />
              Opcionálisan megadhatod, hogy szanjáték vagy színjáték leosztásban
              szeretnél-e játszani.
            </p>
          ) : (
            <p className="text-gray-700">
              Véletlenszerűen kisorsolt parti a keleten ülő ellenjátékosként egy
              kezdő szintű automatikus felvevő ellen.
            </p>
          )}

          <div className="flex justify-center">
            <button
              id="set-parameters"
              className="rounded p-2 bg-gray-200 hover:bg-gray-300"
              onClick={toggleParametersVisibility}
            >
              Paraméterek megadása
            </button>
          </div>

          {parametersVisible && (
            <>
              <hr />

              <div className="flex flex-col items-center justify-center gap-4 text-lg">
                <form className="flex justify-center gap-12">
                  <div className="flex flex-col justify-center gap-2">
                    <span className="font-semibold text-lg">Játékmód</span>
                    <label className="inline-flex items-center gap-2">
                      <input
                        className="accent-green-600 scale-150 focus:ring-2 focus:ring-green-400"
                        type="radio"
                        name="gameMode"
                        id="defaultGame"
                        value="DEFENSE"
                        checked={gameMode === "DEFENSE"}
                        onChange={(e) => setGameMode(e.target.value)}
                      />
                      <span>Hagyományos ellenjáték</span>
                    </label>
                    <label className="inline-flex items-center gap-2">
                      <input
                        className="accent-green-600 scale-150 focus:ring-2 focus:ring-green-400"
                        type="radio"
                        name="gameMode"
                        id="analizeGame"
                        value="ANALYSIS"
                        checked={gameMode === "ANALYSIS"}
                        onChange={(e) => setGameMode(e.target.value)}
                      />
                      <span>Elezmzés*</span>
                    </label>
                  </div>

                  <div className="flex flex-col gap-2">
                    <span className="font-semibold text-lg">
                      Felvevő szintje
                    </span>
                    <label className="inline-flex items-center gap-2">
                      <input
                        className="accent-green-600 scale-150 focus:ring-2 focus:ring-green-400"
                        type="radio"
                        name="declarerLevel"
                        id="beginnerRadio"
                        value="BEGINNER"
                        checked={declarerLevel === "BEGINNER"}
                        onChange={(e) => setDeclarerLevel(e.target.value)}
                      />
                      <span>Kezdő</span>
                    </label>
                    <label className="inline-flex items-center gap-2">
                      <input
                        className="accent-green-600 scale-150 focus:ring-2 focus:ring-green-400"
                        type="radio"
                        name="declarerLevel"
                        id="mediumRadio"
                        value="MEDIUM"
                        checked={declarerLevel === "MEDIUM"}
                        onChange={(e) => setDeclarerLevel(e.target.value)}
                      />
                      <span>Haladó</span>
                    </label>
                  </div>

                  <div className="flex flex-col gap-2">
                    <span className="font-semibold text-lg">
                      Szan- vagy színfelvétel
                    </span>
                    <label className="inline-flex items-center gap-2">
                      <input
                        className="accent-green-600 scale-125 focus:ring-2 focus:ring-green-400"
                        type="checkbox"
                        name="chooseTrump"
                        id="ntRadio"
                        value="NOTRUMP"
                        checked={trumpType === "NOTRUMP"}
                        onChange={() => handleTrumpChange("NOTRUMP")}
                      />
                      <span>Szan</span>
                    </label>
                    <label className="inline-flex items-center gap-2">
                      <input
                        className="accent-green-600 scale-125 focus:ring-2 focus:ring-green-400"
                        type="checkbox"
                        name="chooseTrump"
                        id="colorRadio"
                        value="COLOR"
                        checked={trumpType === "COLOR"}
                        onChange={() => handleTrumpChange("COLOR")}
                      />
                      <span>Szín</span>
                    </label>
                  </div>
                </form>
                <div>*ellenjáték mind a két ellenjátékost irányítva</div>
              </div>
            </>
          )}
        </div>
      </div>

      <Footer></Footer>
    </>
  );
}
