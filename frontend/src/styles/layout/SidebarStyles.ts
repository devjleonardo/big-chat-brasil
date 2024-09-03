import styled from 'styled-components';

export const SidebarContainer = styled.div`
  width: 250px;
  height: 100vh;
  background-color: #343a40;
  color: #fff;
  display: flex;
  flex-direction: column;
  position: fixed;
  left: 0;
  top: 0;
`;

export const SidebarLink = styled.a`
  color: #fff;
  text-decoration: none;
  margin: 15px 0;
  padding: 10px 20px;
  font-size: 18px;

  &:hover {
    color: #007bff;
  }
`;