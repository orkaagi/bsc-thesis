import { Match } from "../../App";

import "../style.css";

interface GameInfoProps {
  gameMode: string;
  data: Match;
  westVisible: boolean;
  northVisible: boolean;
  eastVisible: boolean;
  toggleWest: () => void;
  toggleNorth: () => void;
  toggleEast: () => void;
}

function GameInformation({
  gameMode,
  data,
  westVisible,
  northVisible,
  eastVisible,
  toggleWest,
  toggleNorth,
  toggleEast,
}: GameInfoProps) {
  return (
    <>
      <div id="game-infos" className="flex flex-col gap-4 min-w-[220px]">
        <div className="text-lg font-semibold">Felvétel: {data.contract}</div>
        <div className="text-lg font-semibold">
          Ütések: NS - {data.score_NS}, EW - {data.score_EW}
        </div>

        <hr />

        {gameMode === "ANALYSIS" && (
          <>
            <div className="form-check form-switch">
              <input
                className="form-check-input"
                type="checkbox"
                id="switch-west"
                checked={westVisible}
                onChange={toggleWest}
              />
              <label className="form-check-label" htmlFor="switch-west">
                {westVisible
                  ? "Nyugat kártyáinak elrejtése"
                  : "Nyugat kártyáinak mutatása"}
              </label>
            </div>
            <div className="form-check form-switch">
              <input
                className="form-check-input"
                type="checkbox"
                id="switch-north"
                checked={northVisible}
                onChange={toggleNorth}
              />
              <label className="form-check-label" htmlFor="switch-north">
                {northVisible
                  ? "Észak kártyáinak elrejtése"
                  : "Észak kártyáinak mutatása"}
              </label>
            </div>
            <div className="form-check form-switch">
              <input
                className="form-check-input"
                type="checkbox"
                id="switch-east"
                checked={eastVisible}
                onChange={toggleEast}
              />
              <label className="form-check-label" htmlFor="switch-east">
                {eastVisible
                  ? "Kelet kártyáinak elrejtése"
                  : "Kelet kártyáinak mutatása"}
              </label>
            </div>
            <hr />
          </>
        )}
      </div>
    </>
  );
}

export default GameInformation;
