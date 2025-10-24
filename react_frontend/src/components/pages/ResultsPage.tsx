import {
  FaRegStar,
  FaStar,
  FaTimes,
  FaBox,
  FaBook,
  FaBookOpen,
  FaPlay,
} from "react-icons/fa";

import axios from "axios";
import { useEffect, useState } from "react";
import { useAuth } from "../authentication/AuthProvider";
import Header from "../common_elements/Header";
import Footer from "../common_elements/Footer";

interface Statistics {
  contract: string;
  difference: number;
  gameId: number;
  gameEndedAt: string;
  id: number;
  level: string;
  mode: string;
  result: string;
}

export default function ProfileData() {
  const [data, setData] = useState<Statistics[]>([]);

  const { isAuthenticated, user, token, login, logout } = useAuth();

  const loadData = () => {
    axios
      .get("http://localhost:8083/api/results", {
        params: { username: user },
        headers: { Authorization: `Bearer ${token}` },
      })
      .then((response) => {
        setData(response.data);
        console.log("Statistics data: ", response.data);
      })
      .catch((error) => {
        console.error("Data load", error);
      });
  };

  const startNewGameById = (id: string, mode: string, level: string) => {
    localStorage.setItem("game_id", id);
    localStorage.setItem("game_mode", mode);
    localStorage.setItem("declarer_level", level);
    localStorage.setItem("trump_type", "");
    window.location.href = "index.html";
  };

  function returnResultIcon(result: string) {
    if (result == "WON") {
      return <FaStar className="text-yellow-400" />;
    } else if (result == "LOST") {
      return <FaTimes className="text-red-600" />;
    }
    return <FaBox className="text-yellow-600" />;
  }

  function returnPlayIconClass(result: string) {
    if (result === "WON") {
      return "rounded p-1.5 bg-yellow-300 hover:bg-yellow-400";
    } else if (result === "LOST") {
      return "rounded p-1.5 bg-red-600 hover:bg-red-700";
    }
    return "rounded p-1.5 bg-yellow-600 hover:bg-yellow-700";
  }

  useEffect(() => {
    loadData();
  }, []);

  useEffect(() => {
    console.log("bejelentkezett?", isAuthenticated);
  }, [isAuthenticated]);

  useEffect(() => {
    console.log("felhasznalo:", user);
  }, []);

  useEffect(() => {
    console.log("token:", token);
  }, [token]);

  return (
    <>
      <Header firstRow="Eredmények" secondRow="" currentPage="results" />
      <div
        id="main-content"
        className="flex justify-center items-start gap-6 px-4 py-8 w-full flex-1"
      >
        <div className="flex flex-col gap-2 mt-4 h-full">
          <h2 className="text-xxl">Üdv, {user}!</h2>
          <div id="result-texts" className="mb-4 text-xl text-gray-700">
            <p id="explain-text">
              Ezen az oldalon látszanak az eredményeid. Ha egy adott leosztást
              szeretnél lejátszani az adott sorban szereplő paraméterekkel,
              kattints a sor végén lévő <FaPlay /> ikonra.
            </p>
            <div className="flex items-center gap-4">
              <div className="flex items-center gap-2">
                <span>Jelmagyarázat:</span>
              </div>
              <div className="flex items-center gap-2">
                <FaBox className="text-yellow-600" />
                <span>ezt a meccset még nem játszottad le</span>
              </div>
              <div className="flex items-center gap-2">
                <FaStar className="text-yellow-400" />
                <span>sikeresen megbuktattad a felvételt</span>
              </div>
              <div className="flex items-center gap-2">
                <FaTimes className="text-red-600" />
                <span>nem sikerült megbuktatnod a felvételt</span>
              </div>
            </div>
          </div>

          <table className="table-auto text-xl bg-white rounded shadow text-center">
            <thead className="bg-gray-200">
              <tr>
                <th className="px-2 py-2">
                  Leosztás
                  <br />
                  száma
                </th>
                <th className="px-2 py-2">Felvétel</th>
                <th className="px-2 py-2">Eredmény</th>
                <th className="px-2 py-2">
                  Felvevő
                  <br />
                  ütéskülönbsége
                </th>
                <th className="px-2 py-2">Ellenjáték módja</th>
                <th className="px-2 py-2">Felvevő szintje</th>
                <th className="px-2 py-2">Utolsó játék vége</th>
                <th className="px-2 py-2">Játék</th>
              </tr>
            </thead>
            <tbody>
              {data.length === 0 ? (
                <tr>
                  <td colSpan={7} className="p-4">
                    Nincs adat
                  </td>
                </tr>
              ) : (
                data.map((stat) => (
                  <tr key={stat.id} className="border-t">
                    <td className="px-1 py-1">{stat.gameId}</td>
                    <td>{stat.contract}</td>
                    <td>{returnResultIcon(stat.result)}</td>
                    <td>{stat.result === "NO_DATA" ? "" : stat.difference}</td>
                    <td>
                      {stat.mode === "DEFENSE" ? "hagyományos" : "elemző"}
                    </td>
                    <td>{stat.level === "BEGINNER" ? "kezdő" : "haladó"}</td>
                    <td>{stat.gameEndedAt}</td>
                    <td>
                      <button
                        id="start-new-game-by-id"
                        className="rounded p-1.5 bg-blue-600 hover:bg-blue-700 text-white"
                        onClick={() => {
                          startNewGameById(
                            stat.gameId.toString(),
                            stat.mode,
                            stat.level
                          );
                        }}
                      >
                        <FaPlay className="text-sm" />
                      </button>
                    </td>
                  </tr>
                ))
              )}
            </tbody>
          </table>
        </div>
      </div>

      <Footer></Footer>
    </>
  );
}
