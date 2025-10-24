import { Player } from "../../App";

import "../style.css";

interface CardListProps {
  player: Player;
  visibility: boolean;
  playCard: (seat: string, item: string, index: number, suit: string) => void;
}

function CardList({ player, visibility, playCard }: CardListProps) {
  const suits = player.suits;
  const spades = suits.SPADES.split("") ?? [];
  const hearts = suits.HEARTS.split("") ?? [];
  const diamonds = suits.DIAMONDS.split("") ?? [];
  const clubs = suits.CLUBS.split("") ?? [];

  return (
    <>
      <section className="text-center border border-gray-400 bg-green-200 rounded pt-1 px-1 w-fullC">
        <ul
          className={
            player.seat == "NORTH" || player.seat == "SOUTH"
              ? "list-group list-group-horizontal w-full justify-center"
              : "list-group w-full justify-center"
          }
        >
          {spades.map((item: string, index: number) => (
            <li
              className="list-group-item spades flex items-center"
              key={item}
              onClick={() => {
                playCard(player.seat, item, index, "SPADES");
              }}
            >
              {visibility && <span className="card-suit p-0.5">&spades;</span>}
              {visibility && <span className="card-value p-0.5">{item}</span>}
            </li>
          ))}
          {hearts.map((item: string, index: number) => (
            <li
              className="list-group-item hearts flex items-center"
              key={item}
              onClick={() => {
                playCard(player.seat, item, index, "HEARTS");
              }}
            >
              {visibility && <span className="card-suit p-0.5">&hearts;</span>}
              {visibility && <span className="card-value p-0.5">{item}</span>}
            </li>
          ))}
          {diamonds.map((item: string, index: number) => (
            <li
              className="list-group-item diamonds flex items-center"
              key={item}
              onClick={() => {
                playCard(player.seat, item, index, "DIAMONDS");
              }}
            >
              {visibility && <span className="card-suit p-0.5">&#x25C6;</span>}
              {visibility && <span className="card-value p-0.5">{item}</span>}
            </li>
          ))}
          {clubs.map((item: string, index: number) => (
            <li
              className="list-group-item clubs flex items-center"
              key={item}
              onClick={() => {
                playCard(player.seat, item, index, "CLUBS");
              }}
            >
              {visibility && <span className="card-suit p-0.5">&clubs;</span>}
              {visibility && <span className="card-value p-0.5">{item}</span>}
            </li>
          ))}
        </ul>
        <p className="text-lg m-1">{player.seat}</p>
      </section>
    </>
  );
}

export default CardList;
