import React, { createContext, useContext, useState, ReactNode, useEffect } from 'react';

interface UsuarioContextProps {
  perfil: string | null;
  nomePessoa: string | null;
  setPerfil: (perfil: string) => void;
  setNomePessoa: (nomePessoa: string) => void;
}

const UsuarioContext = createContext<UsuarioContextProps>({
  perfil: null,
  nomePessoa: null,
  setPerfil: () => {},
  setNomePessoa: () => {},
});

export const UsuarioProvider: React.FC<{ children: ReactNode }> = ({ children }) => {
  const [perfil, setPerfil] = useState<string | null>(null);
  const [nomePessoa, setNomePessoa] = useState<string | null>(null);

  useEffect(() => {
    const savedPerfil = localStorage.getItem('perfil');
    const savedNomePessoa = localStorage.getItem('nomePessoa');

    if (savedPerfil) {
      setPerfil(savedPerfil);
    }

    if (savedNomePessoa) {
      setNomePessoa(savedNomePessoa);
    }
  }, []);

  return (
    <UsuarioContext.Provider value={{ perfil, nomePessoa, setPerfil, setNomePessoa }}>
      {children}
    </UsuarioContext.Provider>
  );
};

export const useUser = () => useContext(UsuarioContext);
