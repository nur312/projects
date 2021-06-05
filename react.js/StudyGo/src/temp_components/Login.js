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
        <img src='https://artforlife.ru/wp-content/uploads/2019/02/pre-slack-vyzyvaet-eshhe-bolshee-vozmushhenie-s-pomoshhyu-tvika-k-novomu-logotipu.jpg' />
        <h1>Sign in to the StudyGo</h1>
        <p>studygo.com</p>
        <Button type='submit' onClick={signIn}>
          Sign in with Google
        </Button>
      </LoginInner>
    </LoginContainer>
  );
}

export default Login;

const LoginContainer = styled.div`
  background-color: #f8f8f8;
  height: 100vh;
  display: grid;
  place-items: center;
`;
const LoginInner = styled.div`
  padding: 100px;
  text-align: center;
  background-color: white;
  border-radius: 10px;

  box-shadow: 0 1px 3px rgba(0, 0, 0, 0.12), 0 1px 2px rgba(0, 0, 0, 0.24);

  > img {
    object-fit: contain;
    height: 100px;
    margin-bottom: 30px;
  }

  > button {
    margin-top: 30px;
    text-transform: inherit !important;
    background-color: #0a8d48 !important;
    color: white;
  }
`;
