class QuestionsController < ApplicationController
    before_action :set_question, only: [:show, :edit, :update, :destroy]
  
    # GET /widgets
    # GET /widgets.json
    def index
      @questions = Question.all
    end
  
    # GET /widgets/1
    # GET /widgets/1.json
    def show
    end
  
    # GET /widgets/new
    def new
      @question = Question.new
    end
  
    # GET /widgets/1/edit
    def edit
    end
  
    # POST /widgets
    # POST /widgets.json
    def create
      @question = Question.new(question_params)
  
      respond_to do |format|
        if @question.save
          format.html { redirect_to @question, notice: 'Widget was successfully created.' }
          format.json { render :show, status: :created, location: @question }
        else
          format.html { render :new }
          format.json { render json: @question.errors, status: :unprocessable_entity }
        end
      end
    end
  
    # PATCH/PUT /widgets/1
    # PATCH/PUT /widgets/1.json
    def update
      respond_to do |format|
        if @question.update(question_params)
          format.html { redirect_to @question, notice: 'Widget was successfully updated.' }
          format.json { render :show, status: :ok, location: @question }
        else
          format.html { render :edit }
          format.json { render json: @question.errors, status: :unprocessable_entity }
        end
      end
    end
  
    # DELETE /widgets/1
    # DELETE /widgets/1.json
    def destroy
      @question.destroy
      respond_to do |format|
        format.html { redirect_to questions_url, notice: 'Widget was successfully destroyed.' }
        format.json { head :no_content }
      end
    end
  
    private
      # Use callbacks to share common setup or constraints between actions.
      def set_question
        @question = Question.find(params[:id])
      end
  
      # Never trust parameters from the scary internet, only allow the white list through.
      def question_params
        params.require(:question).permit(:site_name, :security_question, :security_answer)
      end
  end
  