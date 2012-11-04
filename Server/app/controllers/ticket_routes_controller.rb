class TicketRoutesController < ApplicationController
  # GET /ticket_routes
  # GET /ticket_routes.json
  def index
    @ticket_routes = TicketRoute.all

    respond_to do |format|
      format.html # index.html.erb
      format.json { render json: @ticket_routes }
    end
  end

  # GET /ticket_routes/1
  # GET /ticket_routes/1.json
  def show
    @ticket_route = TicketRoute.find(params[:id])

    respond_to do |format|
      format.html # show.html.erb
      format.json { render json: @ticket_route }
    end
  end

  # GET /ticket_routes/new
  # GET /ticket_routes/new.json
  def new
    @ticket_route = TicketRoute.new

    respond_to do |format|
      format.html # new.html.erb
      format.json { render json: @ticket_route }
    end
  end

  # GET /ticket_routes/1/edit
  def edit
    @ticket_route = TicketRoute.find(params[:id])
  end

  # POST /ticket_routes
  # POST /ticket_routes.json
  def create
    @ticket_route = TicketRoute.new(params[:ticket_route])

    respond_to do |format|
      if @ticket_route.save
        format.html { redirect_to @ticket_route, notice: 'Ticket route was successfully created.' }
        format.json { render json: @ticket_route, status: :created, location: @ticket_route }
      else
        format.html { render action: "new" }
        format.json { render json: @ticket_route.errors, status: :unprocessable_entity }
      end
    end
  end

  # PUT /ticket_routes/1
  # PUT /ticket_routes/1.json
  def update
    @ticket_route = TicketRoute.find(params[:id])

    respond_to do |format|
      if @ticket_route.update_attributes(params[:ticket_route])
        format.html { redirect_to @ticket_route, notice: 'Ticket route was successfully updated.' }
        format.json { head :no_content }
      else
        format.html { render action: "edit" }
        format.json { render json: @ticket_route.errors, status: :unprocessable_entity }
      end
    end
  end

  # DELETE /ticket_routes/1
  # DELETE /ticket_routes/1.json
  def destroy
    @ticket_route = TicketRoute.find(params[:id])
    @ticket_route.destroy

    respond_to do |format|
      format.html { redirect_to ticket_routes_url }
      format.json { head :no_content }
    end
  end
end
