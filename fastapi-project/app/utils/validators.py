"""Validators utility functions"""
import re


def validate_email(email: str) -> bool:
    """
    Validate email format
    
    Args:
        email: Email address to validate
    
    Returns:
        True if email is valid, False otherwise
    """
    pattern = r'^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\.[a-zA-Z]{2,}$'
    return bool(re.match(pattern, email))


def validate_string_length(text: str, min_length: int = 1, max_length: int = 255) -> bool:
    """
    Validate string length
    
    Args:
        text: String to validate
        min_length: Minimum length (default: 1)
        max_length: Maximum length (default: 255)
    
    Returns:
        True if string length is valid, False otherwise
    """
    if not isinstance(text, str):
        return False
    return min_length <= len(text) <= max_length
