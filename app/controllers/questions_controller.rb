class QuestionsController < ApplicationController
    def index
        @questions = Question.all
    end

    def new
    end

    def show
        @question = Question.find(params[:id])
    end

    def create
        @question = Question.new(question_params)
        @question.save
        redirect_to @question
    end

    def update
        @question = Question.find(params[:id])
       
        if @question.update(question_params)
          redirect_to @question
        else
          render 'edit'
        end
    end

    def edit
        @question = Question.find(params[:id])
    end


    def destroy
        @question = Question.find(params[:id])
        @question.destroy
    
        redirect_to questions_path
    end

    private
        def question_params
            params.require(:question).permit(:site_name,:security_question, :security_answer)
        end
end
