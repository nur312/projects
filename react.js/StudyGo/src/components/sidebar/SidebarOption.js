import React from 'react';
import styled from 'styled-components';

function SidebarOption({ Icon, title }) {
  return (
    <SidebarOptionContainer>
      <Icon />
      <h3>{title}</h3>
    </SidebarOptionContainer>
  );
}

export default SidebarOption;

const SidebarOptionContainer = styled.div`
  display: flex;
  font-size: 12px;
  align-items: center;
  padding: 10px;
  cursor: pointer;
  width: 100%;

  :hover {
    opacity: 0.9;
    background-color: #340e36;
  }

  > h3 {
    font-weight: 500;
    color: white;
  }

  > h3 > span {
    padding: 15px;
  }

  > .MuiSvgIcon-root {
    margin-right: 0.5rem;
    color: white;
  }
`;
