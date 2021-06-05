import { Button } from '@material-ui/core';
import React from 'react';
import styled from 'styled-components';
import { auth, provider } from '../firebase';

function Login() {
  const signIn = (e) => {
    e.preventDefault();

    auth.signInWithPopup(provider).catch((error) => alert(error.message));
  };
  return (
    <LoginContainer>
      <LoginInner>
        <img src='https://i.pinimg.com/originals/20/7a/2b/207a2b17fb9dfb995097e923c53eec1f.gif' />
        <h2>Sign in StudyGo</h2>

        <Button type='submit' onClick={signIn}>
          Sign in with Google
        </Button>
      </LoginInner>
    </LoginContainer>
  );
}

export default Login;

const LoginContainer = styled.div`
  background-color: var(--background-color);
  display: grid;
  place-items: center;
  height: 100vh;
`;
const LoginInner = styled.div`
  padding: 5rem;
  text-align: center;
  background-color: white;
  border-radius: 10px;

  box-shadow: 0 1px 3px rgba(0, 0, 0, 0.12), 0 1px 2px rgba(0, 0, 0, 0.24);

  > img {
    object-fit: contain;
    height: 10rem;
    margin-bottom: 2rem;
  }

  > button {
    margin-top: 2rem;
    text-transform: inherit !important;
    background-color: #0a8d48 !important;
    color: white;
  }
`;
