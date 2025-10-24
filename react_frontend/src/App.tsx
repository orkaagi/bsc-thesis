import CurrentRound from "./components/game_elements/CurrentRound";
import CardList from "./components/game_elements/CardList";
import Header from "./components/common_elements/Header";
import Footer from "./components/common_elements/Footer";
import GameOverModal from "./components/game_elements/GameOverModal";
import GameInformation from "./components/game_elements/GameInformation";
import { useAuth } from "./components/authentication/AuthProvider";

import { useState, useEffect } from "react";
import axios from "axios";

export type Seat = "NORTH" | "SOUTH" | "EAST" | "WEST";

export interface Player {
  seat: Seat;
  suits: Record<"SPADES" | "HEARTS" | "DIAMONDS" | "CLUBS", string>;
}

export interface Match {
  score_NS: number;
  score_EW: number;
  contract: string;
  players: Record<Seat, Player>;
  round: Round;
  gameOver: boolean;
  gameId: number;
}

export interface Card {
  first: string;
  second: string;
}

export interface Round {
  starterIndex: number;
  currentIndex: number;
  isNewRound: boolean;
  round: Array<Card>;
  positions: Record<"NORTH" | "SOUTH" | "EAST" | "WEST", number>;
}

interface Result {
  first: "LOST" | "WON";
  second: number;
}

function App() {
  const [data, setData] = useState<Match | null>();

  const [result, setResult] = useState<Result | null>();

  const { isAuthenticated, user, token, login, logout } = useAuth();

  const [playing, setPlaying] = useState(false);

  const [isModalOpen, setIsModalOpen] = useState(false);

  const [gameId, setGameId] = useState<string>(() => {
    const stored = localStorage.getItem("game_id");
    return stored === undefined || stored === null ? "-1" : stored;
  });

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

  const [eastVisibility, setEastVisibility] = useState(true);
  const [westVisibility, setWestVisibility] = useState(gameMode === "ANALYSIS");
  const [northVisibility, setNorthVisibility] = useState(false);

  function delay(ms: number) {
    return new Promise((r) => setTimeout(r, ms));
  }

  useEffect(() => {
    console.log(gameId, Number(gameId), gameMode, declarerLevel, trumpType);
    if (!playing) {
      setPlaying(true);
      loadNewGame();
    }
  }, []);

  // az adatok egy hivas utan valtoznak meg --> ellenorizni, hogy tart-e  meg a jatek, es ha igen felvevo
  useEffect(() => {
    if (data != null) {
      if (data.gameOver) {
        console.log("jatek vege");
        setPlaying(false);
        loadStatistics();
        setIsModalOpen(true);
      } else {
        if (data.round.currentIndex == 1) {
          console.log("call for south");
          playAutomatic("SOUTH");
        } else if (gameMode === "DEFENSE" && data.round.currentIndex == 2) {
          console.log("call for west");
          playAutomatic("WEST");
        } else if (data.round.currentIndex == 3) {
          console.log("call for north");
          playAutomatic("NORTH");
        }
      }
    }
  }, [data]);

  const loadStatistics = async () => {
    await delay(3500);
    if (isAuthenticated) {
      console.log(
        "gameId: ",
        localStorage.getItem("game_id"),
        " username: ",
        user,
        " mode: ",
        localStorage.getItem("game_mode"),
        " level: ",
        localStorage.getItem("declarer_level")
      );
      axios
        .get("http://localhost:8083/game/over/auth", {
          params: {
            gameId: localStorage.getItem("game_id"),
            username: user,
            mode: localStorage.getItem("game_mode"),
            level: localStorage.getItem("declarer_level"),
          },
          headers: { Authorization: `Bearer ${token}` },
        })
        .then((response) => {
          console.log("Statistics loaded successfully", response.data);

          setResult(response.data);
        })
        .catch((error) => {
          console.error("Error when loading statistics", error);
        });
    } else {
      axios
        .get("http://localhost:8083/game/over", {
          headers: { Authorization: "" },
        })
        .then((response) => {
          console.log("Statistics loaded", response.data);
          setResult(response.data);
        })
        .catch((error) => {
          console.error("Error when loading statistics", error);
        });
    }
  };

  const loadNewGame = () => {
    console.log(
      "gameId: ",
      localStorage.getItem("game_id"),
      " username: ",
      user,
      " mode: ",
      localStorage.getItem("game_mode"),
      " level: ",
      localStorage.getItem("declarer_level"),
      "trump: ",
      localStorage.getItem("trump_type")
    );
    axios
      .get("http://localhost:8083/game/start/params", {
        params: {
          level: declarerLevel,
          mode: gameMode,
          trumpType: trumpType,
          id: Number(gameId),
        },
        headers: { Authorization: "" },
      })
      .then((response) => {
        console.log("Page refreshed successfully", response.data);
        setData(response.data);
        localStorage.setItem("game_id", response.data.gameId);
        console.log("game_id beallitva: ", localStorage.getItem("game_id"));
      })
      .catch((error) => {
        console.error("Error when refreshing the page", error);
      });
  };

  const playCard = (
    seat: string,
    item: string,
    index: number,
    suit: string
  ) => {
    console.log(seat, suit, item, index);
    if (
      (seat == "EAST" && eastVisibility) ||
      (seat == "WEST" && westVisibility)
    ) {
      axios
        .get("http://localhost:8083/game/play/card", {
          params: {
            seat: seat,
            suit: suit,
            card: item,
            ind: index,
            mode: gameMode,
          },
          headers: { Authorization: "" },
        })
        .then((response) => {
          console.log("Card played successfully", response.data);
          setData(response.data);
        })
        .catch((error) => {
          console.error("Error when playing a card", error);
        });
    }
  };

  const playAutomatic = async (seat: string) => {
    console.log("hello from " + seat);
    await delay(2000);
    axios
      .get("http://localhost:8083/game/play/automatic", {
        params: { seat: seat, mode: gameMode },
        headers: { Authorization: "" },
      })
      .then((response) => {
        console.log("Card played successfully", response.data);
        setData(response.data);
        if (gameMode === "DEFENSE" && response.data.round.currentIndex == 2) {
          console.log("call for west");
          playAutomatic("WEST");
        } else if (
          gameMode === "DEFENSE" &&
          response.data.round.currentIndex == 3
        ) {
          console.log("call for north");
          playAutomatic("NORTH");
        }
      })
      .catch((error) => {
        console.error("Error when playing a card", error);
      });
  };

  const toggleWestVisibility = () => {
    if (gameMode === "ANALYSIS") {
      setWestVisibility((prev) => !prev);
    }
  };

  const toggleNorthVisibility = () => {
    if (gameMode === "ANALYSIS") {
      setNorthVisibility((prev) => !prev);
    }
  };

  const toggleEastVisibility = () => {
    if (gameMode === "ANALYSIS") {
      setEastVisibility((prev) => !prev);
    }
  };

  useEffect(() => {
    console.log("Data:", data);
  }, [data]);

  if (data == null || data == undefined) {
    return (
      <>
        <Header
          firstRow="Online Bridzs Játék"
          secondRow="Automatikus Felvevő Ellen"
          currentPage="index"
        />
        <main className="flex-grow p-2 m-2">
          <p>Betöltés...</p>
        </main>
        <Footer></Footer>
      </>
    );
  }

  return (
    <>
      <Header
        firstRow="Online Bridzs Játék"
        secondRow="Automatikus Felvevő Ellen"
        currentPage="index"
      />

      <div
        id="main-content"
        className="flex justify-center items-start gap-6 px-4 py-8 w-full flex-1"
      >
        <GameInformation
          gameMode={gameMode}
          data={data}
          westVisible={westVisibility}
          northVisible={northVisibility}
          eastVisible={eastVisibility}
          toggleWest={toggleWestVisibility}
          toggleNorth={toggleNorthVisibility}
          toggleEast={toggleEastVisibility}
        />
        <div
          id="game-board"
          className="flex flex-col gap-2 max-w-full overflow-x-auto"
        >
          <div className="grid grid-cols-[auto_1fr_auto] gap-2 items-center justify-items-center w-full">
            <div className="justify-self-start">
              <CardList
                player={data.players.WEST}
                visibility={westVisibility}
                playCard={playCard}
              />
            </div>

            <div className="flex flex-col items-center h-full">
              <CardList
                player={data.players.NORTH}
                visibility={northVisibility}
                playCard={() => {}}
              />
              <CurrentRound
                starterIndex={data.round.starterIndex}
                currentIndex={data.round.currentIndex}
                isNewRound={data.round.isNewRound}
                round={data.round.round}
                positions={data.round.positions}
              />
              <CardList
                player={data.players.SOUTH}
                visibility={true}
                playCard={() => {}}
              />
            </div>

            <div className="justify-self-end">
              <CardList
                player={data.players.EAST}
                visibility={eastVisibility}
                playCard={playCard}
              />
            </div>
          </div>
        </div>
      </div>

      <GameOverModal
        isOpen={isModalOpen}
        won={result?.first == "WON"}
        difference={result?.second ?? 0}
        onClose={() => setIsModalOpen(false)}
      ></GameOverModal>

      <Footer></Footer>
    </>
  );
}

export default App;
