// https://getbootstrap.com/docs/4.0/components/modal/

import { useAuth } from "../authentication/AuthProvider";

interface GameOverModalProps {
  isOpen: boolean;
  won: boolean;
  difference: number;
  onClose: () => void;
}

function differenceMessage(difference: number) {
  if (difference == 0) {
    return "A felvevő teljesítte a felvételt.";
  }
  if (difference > 0) {
    return "A felvevő +" + difference + " ütéssel teljesítte a felvételt.";
  }
  return "A felvevő " + difference + " ütéssel bukta a felvételt.";
}

export default function GameOverModal({
  isOpen,
  won,
  difference,
  onClose,
}: GameOverModalProps) {
  const { isAuthenticated, user, token, login, logout } = useAuth();

  if (!isOpen) {
    return null;
  }

  return (
    <>
      <div className="fixed inset-0 flex items-center justify-center bg-black bg-opacity-50 z-50">
        <div className="relative bg-white shadow-lg rounded-lg p-6 max-w-md w-full">
          <button
            onClick={onClose}
            className="absolute top-2 right-2 text-gray-500 hover:text-gray-700 text-xl"
          >
            &times;
          </button>

          <h2 className="text-xl font-semibold mb-4">
            {won
              ? "Sikerült megbuktatnod a felvételt!"
              : "Nem sikerült megbuktatnod a felvételt!"}
          </h2>
          <p className="mb-4">{differenceMessage(difference)}</p>
          <div className="flex flex-items-center gap-4">
            {isAuthenticated && (
              <a
                href="results.html"
                onClick={onClose}
                className="flex items-center gap-2 p-2 text-white rounded mt-auto self-start bg-[#b1c64f] text-decoration-none"
              >
                <div className="text-[#22390b]">Ererdmények</div>
              </a>
            )}
            <a
              href="start.html"
              onClick={onClose}
              className="flex items-center gap-2 p-2 rounded mt-auto self-start bg-[#4c8613] text-decoration-none"
            >
              <span className="text-white">Új játék</span>
            </a>
          </div>
        </div>
      </div>
    </>
  );
}
