package com.beehyv.DemoProject;

import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

@Service
public class UserService {
	
	@Autowired
	UserRepository userRepository;
    @Autowired
    EvaluatorService evaluatorService;

	public String mostUsed(User user) {
		Map<Character,Integer> hm = new HashMap<Character,Integer>();
		hm.put('+', user.getPlus());
		hm.put('-', user.getMinus());
		hm.put('*', user.getMulti());
		hm.put('/', user.getDivide());
		
		 Map<Character, Integer> sorted = hm.entrySet().stream() .sorted(Collections.reverseOrder(Map.Entry.comparingByValue())) .collect( Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue, (e1, e2) -> e2, LinkedHashMap::new));
		 
		 String s = "";
		 int max = -1;
		 for(Map.Entry<Character, Integer> entry: sorted.entrySet()) {
			 if(entry.getValue()==max) {
				 s+=" "+entry.getKey();
			 }else {
				 if(max!=-1)
					 break;
				 max = entry.getValue();
				 s+=" "+entry.getKey();
			 }
		 }
		 if(max ==0)
			 return "No operator used yet";
		return s;
	}
	
	public User updateCounts(Long userId, String expression) {
		
		int countPlus = StringUtils.countOccurrencesOf(expression, "+");
		int countMinus = StringUtils.countOccurrencesOf(expression, "-");
		int countDivide = StringUtils.countOccurrencesOf(expression, "/");
		int countMult = StringUtils.countOccurrencesOf(expression, "*");
		 Optional<User> userdb = userRepository.findById(userId);
		 if(userdb.isPresent()) {
			 countPlus += userdb.get().getPlus();
			 countMinus += userdb.get().getMinus();
			 countMult += userdb.get().getMulti();
			 countDivide += userdb.get().getDivide();
 	    }
		 	User user = new User();
		 	user.setUserid(userId);
			user.setPlus(countPlus);
			user.setMinus(countMinus);
			user.setMulti(countMult);
			user.setDivide(countDivide);
		return userRepository.save(user);
	}
	
	public String calculateAndUpdateUser(Long userId, Expression expression) {
		String result = null;
    	try {
    		result = evaluatorService.eval(expression.getExpression()) + "";
    	}catch(RuntimeException e){
    		return "Invalid expression "+e;
    	}
    	
    	User userSaved = updateCounts(userId, expression.getExpression());
    	if(userSaved == null || StringUtils.isEmpty(userSaved.getUserid())) {
    		return "User is not saved";
    	}
    	return result+"";
	}
	
	public String findUserMostUsedById(long userId){
		Optional<User> user = userRepository.findById(userId);
	    if(user.isPresent()) {
	        return mostUsed(user.get());
	    } else {
	        return "User not found";
	    }
	}
}
