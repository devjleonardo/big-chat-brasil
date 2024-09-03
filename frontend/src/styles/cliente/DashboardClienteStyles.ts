import styled from 'styled-components';

export const DashboardContainer = styled.div`
  margin-left: 250px;
  padding: 20px;
  background-color: #f4f6f8;
  min-height: 100vh;
  margin-top: 60px;
`;

export const DashboardTitle = styled.h1`
  font-size: 24px;
  margin-bottom: 20px;
`;

export const InfoCard = styled.div<{ color?: string }>`
  background-color: ${({ color }) => color || '#fff'};
  padding: 15px;
  margin-bottom: 15px;
  border-radius: 8px;
  box-shadow: 0 2px 4px rgba(0, 0, 0, 0.1);
`;

export const InfoLabel = styled.div`
  font-size: 16px;
  font-weight: bold;
  color: #333;
`;

export const InfoValue = styled.div`
  font-size: 18px;
  color: #555;
  margin-top: 5px;
`;
