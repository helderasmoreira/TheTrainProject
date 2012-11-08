class CardsController < ApplicationController
  # GET /cards
  # GET /cards.json
  def index
    @cards = Card.all

    respond_to do |format|
      format.html # index.html.erb
      format.json { render json: @cards }
    end
  end

  # GET /cards/1
  # GET /cards/1.json
  def show
    @card = Card.find(params[:id])

    respond_to do |format|
      format.html # show.html.erb
      format.json { render json: @card }
    end
  end

  # GET /cards/new
  # GET /cards/new.json
  def new
    @card = Card.new

    respond_to do |format|
      format.html # new.html.erb
      format.json { render json: @card }
    end
  end

  # GET /cards/1/edit
  def edit
    @card = Card.find(params[:id])
  end

  def getByUserId
    @cards = Card.where("user_id = ? AND paid = ?", params[:userId], false)
    respond_to do |format|
            format.json { render :json => @cards }
    end
  end

  def addCard
    @card = Card.where("number = ?", params[:number])
    if @card.empty?

      @new_card = Card.new(:number => params[:number], :card_type => params[:card_type], 
        :validity => Date.parse(params[:validity]), 
        :user_id => params[:user_id], :cvv => params[:cvv])

      if @new_card.save
          @new_card["status"] = "OK"
          respond_to do |format|
            format.json { render :json => @new_card }
          end
      end
    else
      respond_to do |format|
            format.json { render :json => {:status => "FAILED"} }
          end
    end
  end

def removeCard
  @card = Card.where("number = ?", params[:number])
  if @card.empty?
      respond_to do |format|
          format.json { render :json => {:status => "FAILED"} }
      end
  else
      @card.first.destroy
      respond_to do |format|
          format.json { render :json => {:status => "OK"} }
      end
  end
end



  # POST /cards
  # POST /cards.json
  def create
    @card = Card.new(params[:card])

    respond_to do |format|
      if @card.save
        format.html { redirect_to @card, notice: 'Card was successfully created.' }
        format.json { render json: @card, status: :created, location: @card }
      else
        format.html { render action: "new" }
        format.json { render json: @card.errors, status: :unprocessable_entity }
      end
    end
  end

  # PUT /cards/1
  # PUT /cards/1.json
  def update
    @card = Card.find(params[:id])

    respond_to do |format|
      if @card.update_attributes(params[:card])
        format.html { redirect_to @card, notice: 'Card was successfully updated.' }
        format.json { head :no_content }
      else
        format.html { render action: "edit" }
        format.json { render json: @card.errors, status: :unprocessable_entity }
      end
    end
  end

  # DELETE /cards/1
  # DELETE /cards/1.json
  def destroy
    @card = Card.find(params[:id])
    @card.destroy

    respond_to do |format|
      format.html { redirect_to cards_url }
      format.json { head :no_content }
    end
  end
end
