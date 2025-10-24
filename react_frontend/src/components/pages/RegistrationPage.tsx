// CSS: https://tailwindcss.com/plus/ui-blocks/application-ui/forms/form-layouts
// html: https://getbootstrap.com/docs/4.1/components/forms/#form-controls
// final: https://v1.tailwindcss.com/components/forms

import axios from "axios";
import { FormEvent, useEffect, useState } from "react";

import Header from "../common_elements/Header";
import Footer from "../common_elements/Footer";

function RegistrationForm() {
  const [registationForm, setRegistrationForm] = useState({
    username: "",
    email: "",
    password: "",
  });

  const [registerSuccess, setRegisterSuccess] = useState<string | null>(null);

  const register = async (e: FormEvent) => {
    e.preventDefault();
    if (registationForm.password.length < 8) {
      console.error("Jelszó kevesebb, mint 8 karakter");
      setRegisterSuccess("error");
    }
    axios.defaults.headers.common["Authorization"] = `No-Auth`;
    try {
      const response = await axios.post(
        "http://localhost:8083/api/users",
        registationForm
      );
      console.log("Sign up successful", response.data);
      setRegisterSuccess("success");
      setTimeout(() => {
        window.location.href = "login.html";
      }, 2000);
    } catch (error) {
      console.error("sign up error", error);
      setRegisterSuccess("error");
    }
  };

  useEffect(() => {
    console.log(registationForm);
  }, [registationForm.username]);

  return (
    <>
      <Header firstRow="Regisztráció" secondRow="" currentPage="register" />
      {registerSuccess === "success" && (
        <div
          className="bg-green-100 border border-green-400 text-green-700 px-4 py-3 rounded relative my-4"
          role="alert"
        >
          <strong className="font-bold">Sikeres regisztráció!</strong>
          <span className="block sm:inline">
            {" "}
            Átirányítás a bejelentkezéshez.
          </span>
        </div>
      )}
      {registerSuccess === "error" && (
        <div
          className="bg-red-100 border border-red-400 text-red-700 px-4 py-3 rounded relative my-4"
          role="alert"
        >
          <strong className="font-bold">Sikertelen regisztráció!</strong>
          <span className="block sm:inline">
            {" "}
            Ellenőrizd, hogy a beviteli mezők megfelelnek-e a kritériumoknak.
          </span>
        </div>
      )}
      <div
        id="main-content"
        className="flex flex-col justify-center items-center flex-1 text-xl"
      >
        <form
          className="bg-white shadow-md rounded px-12 py-8 mb-4 grid gap-3"
          onSubmit={register}
        >
          <div className="form-group">
            <label htmlFor="username">
              Felhasználónév<span className="text-red-500">*</span>
            </label>
            <input
              value={registationForm.username}
              type="text"
              className="form-control"
              id="username"
              placeholder="Felhasználó"
              onChange={(e) =>
                setRegistrationForm({
                  ...registationForm,
                  username: e.target.value,
                })
              }
            />
            <small id="usernamelHelp" className="form-text text-muted">
              Kötelező mező, egyedi, 3-20 karakter<br/>nem lehet ékezetes vagy speciális.
            </small>
          </div>
          <div className="form-group">
            <label htmlFor="emailAddress">E-mail cím</label>
            <input
              value={registationForm.email}
              type="email"
              className="form-control"
              id="emailAddress"
              placeholder="E-mail"
              onChange={(e) =>
                setRegistrationForm({
                  ...registationForm,
                  email: e.target.value,
                })
              }
            />
          </div>
          <div className="form-group mb-4">
            <label htmlFor="password">
              Jelszó<span className="text-red-500">*</span>
            </label>
            <input
              value={registationForm.password}
              type="password"
              className="form-control"
              id="password"
              placeholder="******"
              onChange={(e) =>
                setRegistrationForm({
                  ...registationForm,
                  password: e.target.value,
                })
              }
            />
            <small id="passwordlHelp" className="form-text text-muted">
              Kötelező mező, legalább 8 karakter.
            </small>
          </div>
          <div className="flex justify-center">
            <button id="register-button" type="submit" className="rounded p-2">
              Regisztráció
            </button>
          </div>
        </form>

        <div>
          Van már fókod?{" "}
          <a
            className="inline-block align-baseline font-bold"
            href="login.html"
          >
            Bejelentkezés
          </a>
        </div>
      </div>

      <Footer />
    </>
  );
}

export default RegistrationForm;
