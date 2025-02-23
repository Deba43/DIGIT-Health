package org.debadatta.health.repo;

import java.util.Optional;
import java.util.List;

import org.debadatta.health.model.Admin;
import org.debadatta.health.model.Doctors;
import org.debadatta.health.model.Patients;
import org.debadatta.health.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBScanExpression;


@Repository
public class UserRepo {

	@Autowired
	private DynamoDBMapper dynamoDBMapper;

	public void saveUser(User user) {
		dynamoDBMapper.save(user);
	}

	public Optional<User> findByEmail(String email) {
		List<User> users = dynamoDBMapper.scan(User.class, new DynamoDBScanExpression());
		return users.stream().filter(user -> user.getEmail().equals(email)).findFirst();
	}

	public Optional<Admin> findAdminById(Long id) {
		return Optional.ofNullable(dynamoDBMapper.load(Admin.class, id));
	}

	public Optional<Doctors> findDoctorById(Long id) {
		return Optional.ofNullable(dynamoDBMapper.load(Doctors.class, id));
	}

	public Optional<Patients> findPatientById(Long id) {
		return Optional.ofNullable(dynamoDBMapper.load(Patients.class, id));
	}

	public List<User> getAllUsers() {
		return dynamoDBMapper.scan(User.class, new DynamoDBScanExpression());
	}

	public void deleteUserById(Long id) {
		User user = dynamoDBMapper.load(User.class, id);
		if (user != null) {
			dynamoDBMapper.delete(user);
		}
	}
}
