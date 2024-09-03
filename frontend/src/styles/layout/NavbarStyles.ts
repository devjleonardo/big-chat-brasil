import styled from 'styled-components';

export const NavbarContainer = styled.nav`
  width: calc(100% - 250px);
  height: 60px;
  background-color: #343a40;
  color: #fff;
  padding: 0 20px;
  box-shadow: 0 2px 4px rgba(0, 0, 0, 0.1);
  display: flex;
  align-items: center;
  justify-content: space-between;
  position: fixed;
  top: 0;
  left: 250px;
  z-index: 1000;
`;


export const NavbarTitle = styled.h1`
  font-size: 20px;
  color: #fff;
`;

export const LogoutButton = styled.button`
  background-color: transparent;
  border: 1px solid #ecf0f1;
  color: #ecf0f1;
  padding: 5px 10px;
  font-size: 1rem;
  cursor: pointer;
  border-radius: 5px;

  &:hover {
    background-color: #ecf0f1;
    color: #2C3E50;
  }
`;