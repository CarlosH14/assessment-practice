"""User Service - Business logic layer"""
from typing import List, Optional
from app.repositories.user_repository import UserRepository
from app.utils.validators import validate_email


class UserService:
    """Service for user business logic"""
    
    def __init__(self, user_repository: UserRepository):
        self.user_repository = user_repository
    
    def get_all_users(self) -> List[dict]:
        """Get all users"""
        return self.user_repository.get_all()
    
    def get_user_by_id(self, user_id: int) -> Optional[dict]:
        """Get user by ID"""
        return self.user_repository.get_by_id(user_id)
    
    def create_user(self, name: str, email: str) -> dict:
        """Create a new user with validation"""
        if not validate_email(email):
            raise ValueError("Invalid email format")
        
        if not name or len(name.strip()) == 0:
            raise ValueError("Name cannot be empty")
        
        user_data = {
            "name": name.strip(),
            "email": email.lower()
        }
        return self.user_repository.create(user_data)
    
    def update_user(self, user_id: int, name: Optional[str] = None, email: Optional[str] = None) -> Optional[dict]:
        """Update an existing user"""
        existing_user = self.user_repository.get_by_id(user_id)
        if not existing_user:
            return None
        
        user_data = {}
        if name is not None:
            if len(name.strip()) == 0:
                raise ValueError("Name cannot be empty")
            user_data["name"] = name.strip()
        
        if email is not None:
            if not validate_email(email):
                raise ValueError("Invalid email format")
            user_data["email"] = email.lower()
        
        return self.user_repository.update(user_id, user_data)
    
    def delete_user(self, user_id: int) -> bool:
        """Delete a user"""
        return self.user_repository.delete(user_id)
