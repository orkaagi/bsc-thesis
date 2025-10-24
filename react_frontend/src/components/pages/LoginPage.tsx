import { FormEvent, useEffect, useState } from "react";

import Header from "../common_elements/Header";
import Footer from "../common_elements/Footer";
import { useAuth } from "../authentication/AuthProvider";

function LoginForm() {
  const [authData, setAuthData] = useState({
    username: "",
    password: "",
  });

  const [loginSuccess, setLoginSuccess] = useState<string | null>(null);

  const { isAuthenticated, user, token, login, logout } = useAuth();

  const onLogin = async (e: FormEvent) => {
    e.preventDefault();
    const success = await login(authData.username, authData.password);

    if (success) {
      setLoginSuccess("success");
      setTimeout(() => {
        window.location.href = "index.html";
      }, 2000);
    } else {
      setLoginSuccess("error");
    }
  };

  useEffect(() => {
    console.log(authData);
  }, [authData]);

  useEffect(() => {
    console.log("bejelentkezett?", isAuthenticated);
  }, [isAuthenticated]);

  useEffect(() => {
    console.log("token:", token);
  }, [token]);

  useEffect(() => {
    console.log(user);
  }, [user]);

  return (
    <>
      <Header firstRow="Bejelentkezés" secondRow="" currentPage="login" />
      {loginSuccess === "success" && (
        <div
          className="bg-green-100 border border-green-400 text-green-700 px-4 py-3 rounded relative my-4"
          role="alert"
        >
          <strong className="font-bold">Sikeres bejelentkezés!</strong>
          <span className="block sm:inline"> Átirányítás a főoldalra.</span>
        </div>
      )}
      {loginSuccess === "error" && (
        <div
          className="bg-red-100 border border-red-400 text-red-700 px-4 py-3 rounded relative my-4"
          role="alert"
        >
          <strong className="font-bold">Sikertelen bejelentkezés!</strong>
          <span className="block sm:inline">
            {" "}
            Hibás felhasználónév vagy jelszó.
          </span>
        </div>
      )}
      <div
        id="main-content"
        className="flex flex-col justify-center items-center flex-1 text-xl"
      >
        <form
          className="bg-white shadow-md rounded px-12 py-8 mb-4 grid gap-3 md:min-w-[400px]"
          onSubmit={onLogin}
        >
          <div className="form-group">
            <label htmlFor="username">Felhasználónév</label>
            <input
              value={authData.username}
              type="text"
              className="form-control"
              id="username"
              placeholder="Felhasználó"
              onChange={(e) =>
                setAuthData({
                  ...authData,
                  username: e.target.value,
                })
              }
            />
          </div>
          <div className="form-group mb-4">
            <label htmlFor="password">Jelszó</label>
            <input
              value={authData.password}
              type="password"
              className="form-control"
              id="password"
              placeholder="******"
              onChange={(e) =>
                setAuthData({
                  ...authData,
                  password: e.target.value,
                })
              }
            />
          </div>
          <div className="flex justify-center">
            <button id="login-button" type="submit" className="rounded p-2">
              Bejelentkezés
            </button>
          </div>
        </form>

        <div>
          Nincs még fókod?{" "}
          <a
            className="inline-block align-baseline font-bold"
            href="register.html"
          >
            Regisztráció
          </a>
        </div>
      </div>

      <Footer />
    </>
  );
}

export default LoginForm;
