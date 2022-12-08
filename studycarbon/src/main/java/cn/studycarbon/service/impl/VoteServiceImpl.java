package cn.studycarbon.service.impl;

import javax.transaction.Transactional;

import cn.studycarbon.domain.Vote;
import cn.studycarbon.repository.VoteRepository;
import cn.studycarbon.service.VoteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class VoteServiceImpl implements VoteService {

	@Autowired
	private VoteRepository voteRepository;

	@Override
	@Transactional
	public void removeVote(Long id) {
		voteRepository.deleteById(id);
	}

	@Override
	public Vote getVoteById(Long id) {
		return  voteRepository.findById(id).get();
	}

}
