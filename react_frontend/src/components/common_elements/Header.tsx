import "../common_elements_style.css";

import {
  FaInfo,
  FaInfoCircle,
  FaQuestion,
  FaSignOutAlt,
  FaSignInAlt,
  FaRegUserCircle,
  FaHome,
  FaUserCircle,
  FaQuestionCircle,
  FaRegPlusSquare,
  FaPlus,
  FaPlusCircle,
  FaPlusSquare,
  FaPlay,
  FaPlayCircle,
  FaRegPlayCircle,
  FaTable,
  FaBook,
  FaChartBar,
} from "react-icons/fa";

import { useAuth } from "../authentication/AuthProvider";

interface HeaderContent {
  firstRow: string;
  secondRow: string;
  currentPage: string;
}

const Header = ({ firstRow, secondRow, currentPage }: HeaderContent) => {
  const { isAuthenticated, login, logout } = useAuth();

  return (
    <>
      <header className="relative flex items-center justify-center p-12">
        <div className="flex gap-4 absolute left-4 z-50">
          <a
            id={currentPage === "index" ? "nav-to-main-current" : "nav-to-main"}
            href="index.html"
            className="flex items-center gap-2 p-2 rounded"
          >
            <FaHome />
            Főoldal
          </a>
          <a
            id={
              currentPage === "start" ? "nav-to-start-current" : "nav-to-start"
            }
            href="start.html"
            className="flex items-center gap-2 p-2 rounded"
          >
            <FaPlusCircle />
            Új játék
          </a>

          {isAuthenticated && (
            <a
              id={
                currentPage === "results"
                  ? "nav-to-results-current"
                  : "nav-to-results"
              }
              href="results.html"
              className="flex items-center gap-2 p-2 rounded"
            >
              <FaBook />
              Eredmények
            </a>
          )}
        </div>
        <h1 className="absolute text-center left-1/2 transform -translate-x-1/2 text-3xl font-bold mt-2">
          {firstRow} <br /> {secondRow}
        </h1>
        <div className="flex gap-4 absolute right-4 z-50">
          {!isAuthenticated && (
            <a
              id={
                currentPage === "register"
                  ? "nav-to-register-current"
                  : "nav-to-register"
              }
              href="register.html"
              className="flex items-center gap-2 p-2 rounded"
            >
              <FaRegUserCircle /> Regisztráció
            </a>
          )}
          {!isAuthenticated && (
            <a
              id={
                currentPage === "login"
                  ? "nav-to-login-current"
                  : "nav-to-login"
              }
              href="login.html"
              className="flex items-center gap-2 p-2 rounded"
            >
              <FaSignInAlt /> Bejelentkezés
            </a>
          )}
          {isAuthenticated && (
            <button
              id="log-out"
              className="flex items-center gap-2 p-2 rounded"
              onClick={logout}
            >
              <FaSignOutAlt />
              Kijelentkezés
            </button>
          )}
          <a
            id={currentPage === "help" ? "nav-to-help-current" : "nav-to-help"}
            href="help.html"
            className="flex items-center gap-2 p-2 rounded"
          >
            <FaQuestionCircle /> Súgó
          </a>
        </div>
      </header>
    </>
  );
};

export default Header;
