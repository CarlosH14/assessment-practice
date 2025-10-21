"""FastAPI Application Entry Point"""
from fastapi import FastAPI
from app.controllers import user_controller

# Create FastAPI app
app = FastAPI(
    title="FastAPI Clean Architecture Example",
    description="A simple FastAPI project with clean architecture structure",
    version="1.0.0"
)

# Include routers
app.include_router(user_controller.router)


@app.get("/")
async def root():
    """Root endpoint"""
    return {
        "message": "Welcome to FastAPI Clean Architecture Example",
        "version": "1.0.0",
        "docs": "/docs",
        "api": "/api/users"
    }


@app.get("/health")
async def health_check():
    """Health check endpoint"""
    return {"status": "healthy"}


if __name__ == "__main__":
    import uvicorn
    uvicorn.run(app, host="0.0.0.0", port=8000)
