'use client'
import { useState, useEffect } from 'react'

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
    fetch(`http://localhost:8090/api/v1/members/me`, {
      method: 'GET',
      credentials: 'include', // 핵심 변경점
    })
      .then(result => result.json())
      .then(result => setMember(result.data.memberDTO))
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