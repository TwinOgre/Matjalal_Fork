'use client'
import { useState, useEffect } from 'react'
import api from '../utils/api';

type memberInterface = {
  id: number,
  createdDate: string,
  modifiedDate: string,
  username: string,
  email: string
}
export default function About() {
  const [member, setMember] = useState<memberInterface>();

  useEffect(() => {
    api.get("/members/me")
    .then(response => setMember(response.data.data.memberDTO))
    .catch (err => {
      console.log(err)
    })
  }, [])

  return (
    <div>
      <h1>😎소개 페이지 입니다.</h1>
      아이디: {member?.id} | <br />
      이름: {member?.username} | <br />
      이메일: {member?.email} | <br />
      생성일: {member?.createdDate} |
      <br />
      <br />
    </div>
  );
}