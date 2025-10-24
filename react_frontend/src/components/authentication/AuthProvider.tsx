import {
  ReactNode,
  createContext,
  useContext,
  useState,
  useEffect,
} from "react";
import axios from "axios";

interface AuthContextType {
  isAuthenticated: boolean;
  user: string | null;
  token: string | null;
  login: (username: string, password: string) => Promise<boolean>;
  logout: () => void;
}

const AuthContext = createContext<AuthContextType | undefined>(undefined);

export const AuthProvider: React.FC<{ children: ReactNode }> = ({
  children,
}) => {
  const [token, setToken] = useState<string | null>(() =>
    localStorage.getItem("token")
  );

  const [user, setUser] = useState<string | null>(() =>
    localStorage.getItem("user_name")
  );

  const isAuthenticated = Boolean(token);

  const login = async (
    username: string,
    password: string
  ): Promise<boolean> => {
    try {
      const response = await axios.get("http://localhost:8083/api/token", {
        auth: { username, password },
      });
      console.log("Sign in successful", response.data);
      const jwt = response.data;
      setToken(jwt);
      localStorage.setItem("token", jwt);
      axios.defaults.headers.common[
        "Authorization"
      ] = `Bearer ${response.data}`;

      setUser(username);
      localStorage.setItem("user_name", username);

      console.log(username, user);
      console.log("token: ", response.data);
      return true;
    } catch (error) {
      console.error("Sign in error", error);
      return false;
    }
  };

  const logout = () => {
    setToken(null);
    localStorage.removeItem("token");
    setUser(null);
    localStorage.removeItem("user_name");
  };

  /*
  useEffect(() => {
    const checkToken = async () => {
      if (!token) return;
      try {
        await axios.get("http://localhost:8083/api/token/validate", {
          headers: { Authorization: `Bearer ${token}` },
        });
      } catch (err) {
        logout();
      }
    };
    checkToken();
  }, [token]);
*/

  useEffect(() => {
    if (token) {
      axios.defaults.headers.common["Authorization"] = `Bearer ${token}`;
    } else {
      delete axios.defaults.headers.common["Authorization"];
    }
  }, [token]);

  useEffect(() => {
    console.log("felhasznalo:", user);
  }, [user]);

  return (
    <AuthContext.Provider
      value={{ isAuthenticated, user, token, login, logout }}
    >
      {children}
    </AuthContext.Provider>
  );
};

export const useAuth = (): AuthContextType => {
  const context = useContext(AuthContext);
  if (!context) {
    throw new Error("useAuth must be used within an AuthProvider");
  }
  return context;
};
