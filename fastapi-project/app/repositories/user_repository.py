"""User Repository - Data access layer"""
from typing import List, Optional, Dict


class UserRepository:
    """Repository for user data access"""
    
    def __init__(self):
        # In a real application, this would connect to a database
        self.users: Dict[int, dict] = {
            1: {"id": 1, "name": "Juan Pérez", "email": "juan@example.com"},
            2: {"id": 2, "name": "María García", "email": "maria@example.com"}
        }
        self.next_id = 3
    
    def get_all(self) -> List[dict]:
        """Get all users"""
        return list(self.users.values())
    
    def get_by_id(self, user_id: int) -> Optional[dict]:
        """Get user by ID"""
        return self.users.get(user_id)
    
    def create(self, user_data: dict) -> dict:
        """Create a new user"""
        user = {
            "id": self.next_id,
            **user_data
        }
        self.users[self.next_id] = user
        self.next_id += 1
        return user
    
    def update(self, user_id: int, user_data: dict) -> Optional[dict]:
        """Update an existing user"""
        if user_id not in self.users:
            return None
        self.users[user_id].update(user_data)
        return self.users[user_id]
    
    def delete(self, user_id: int) -> bool:
        """Delete a user"""
        if user_id in self.users:
            del self.users[user_id]
            return True
        return False
