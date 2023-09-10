package com.khali.api3.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.khali.api3.domain.member.Member;
import com.khali.api3.repositories.MembersRepository;

@Service
public class MembersService {
    @Autowired
    private MembersRepository membersRepository;

    public List<Member> getAllMembers(){
        return membersRepository.findAll();
    }

    public Member insertMember(Member member){
        return membersRepository.save(member);
    }
    
    public Member getMemberById(Long id){
        return membersRepository.findById(id).orElse(null);
    }
}
